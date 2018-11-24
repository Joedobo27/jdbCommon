package com.joedobo27.libs;

import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tooling class used with Javassist Bytecode objects.
 * @version 1
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ByteCodeWild {

    /**
     * Internal value used to track changes to this class.
     */
    @SuppressWarnings("unused")
    private static int VERSION = 1;

    /**
     * A class's constPool.
     */
    private final ConstPool constPool;


    /**
     * A method's codeAttribute used to parse its bytecode, work with its JVM LocalVariableAttribute and work with its
     * JVM LineNumberAttribute.
     */
    private final CodeAttribute codeAttribute;

    /**
     * variable names can repeat in the LocalVariableAttribute and have different indexes. This ArrayList is to used to
     * build possible combinations for all those potential names.
     */
    private ArrayList<Bytecode> searchArrays = new ArrayList<>();


    private int foundBytecodePos;

    public ByteCodeWild(ConstPool constPool, CodeAttribute codeAttribute) {
        this.constPool = constPool;
        this.codeAttribute = codeAttribute;
        if (searchArrays.size() == 0) {
            searchArrays.add(new Bytecode(constPool));
        }
    }

    public void addCodeBranching(int opcode, int branchCount) {
        this.searchArrays.forEach(bytecode -> {
            bytecode.addOpcode(opcode);
            bytecode.add((branchCount >>> 8) & 0xFF, branchCount & 0xFF);
        });
    }

    public void addCodeBranchingWild(int opcode) {
        this.searchArrays.forEach(bytecode -> {
            bytecode.addOpcode(opcode);
            bytecode.add(0);
            bytecode.add(0);
        });
    }

    public void addAload(String varName, String varSignature) {
        int[] indexes = lookupVariableSlot(varName, varSignature);
        ArrayList<Bytecode> abc = new ArrayList<>();
        this.searchArrays.forEach(value -> Arrays.stream(indexes)
                .forEach(value1 -> {
                    Bytecode b = copyBytecode(value);
                    b.addAload(value1);
                    abc.add(b);}));
        setSearchArrays(abc);
    }

    public void addAstore(String varName, String varSignature) {
        int[] indexes = lookupVariableSlot(varName, varSignature);
        ArrayList<Bytecode> abc = new ArrayList<>();
        this.searchArrays.forEach(value -> Arrays.stream(indexes)
                .forEach(value1 -> {
                    Bytecode b = copyBytecode(value);
                    b.addAstore(value1);
                    abc.add(b);}));
        setSearchArrays(abc);
    }

    public void addConstZero(CtClass type) throws RuntimeException{
        this.searchArrays.forEach(bytecode -> {
            if (type.isPrimitive()) {
                if (type == CtClass.longType) {
                    bytecode.addOpcode(9);
                } else if (type == CtClass.floatType) {
                    bytecode.addOpcode(11);
                } else if (type == CtClass.doubleType) {
                    bytecode.addOpcode(14);
                } else {
                    if (type == CtClass.voidType) {
                        throw new RuntimeException("void type?");
                    }
                    bytecode.addOpcode(3);
                }
            } else {
                bytecode.addOpcode(1);
            }
        });
    }

    public void addIconst(int n) {
        this.searchArrays.forEach(bytecode -> {
            if (n < 6 && -2 < n) {
                bytecode.addOpcode(3 + n);
            } else if (n <= 127 && -128 <= n) {
                bytecode.addOpcode(16);
                bytecode.add(n);
            } else if (n <= 32767 && -32768 <= n) {
                bytecode.addOpcode(17);
                bytecode.add(n >> 8);
                bytecode.add(n);
            } else {
                bytecode.addLdc(this.constPool.addIntegerInfo(n));
            }
        });
    }

    public void addIload(String varName, String varSignature) {
        int[] indexes = lookupVariableSlot(varName, varSignature);
        ArrayList<Bytecode> abc = new ArrayList<>();
        this.searchArrays.forEach(byteCode -> Arrays.stream(indexes)
                .forEach(tableVar -> {
                    Bytecode b = copyBytecode(byteCode);
                    b.addIload(tableVar);
                    abc.add(b);}));
        setSearchArrays(abc);
    }

    public void addIstore(String varName, String varSignature) {
        int[] indexes = lookupVariableSlot(varName, varSignature);
        ArrayList<Bytecode> abc = new ArrayList<>();
        this.searchArrays.forEach(byteCode -> Arrays.stream(indexes)
                .forEach(tableVar -> {
                    Bytecode b = copyBytecode(byteCode);
                    b.addIstore(tableVar);
                    abc.add(b);}));
        setSearchArrays(abc);
    }

    public void addInvokevirtual(String className, String methodName, String descriptor) {
        this.searchArrays.forEach(bytecode -> bytecode.addInvokevirtual(
                this.constPool.addClassInfo(className.replace("/", ".")), methodName, descriptor));
    }

    public void addInvokestatic(String className, String methodName, String descriptor) {
        this.searchArrays.forEach(bytecode -> bytecode.addInvokestatic(
                this.constPool.addClassInfo(className.replace("/", ".")), methodName, descriptor));
    }

    public void addOpcode(int code) {
        this.searchArrays.forEach(bytecode -> bytecode.addOpcode(code));
    }

    public void trimFoundBytecode() throws RuntimeException{
        int pos = -1;
        int[] founds = new int[this.searchArrays.size()];
        Arrays.fill(founds, 0);
        for (int i = 0; i < this.searchArrays.size(); i++) {
            try {
                pos = findCode(this.searchArrays.get(i).get());
                founds[i] = 1;
            }  catch (NotFoundException ignore) {
            }
        }
        if (Arrays.stream(founds).sum() > 1)
            throw new RuntimeException("Multiple matches found in trimFoundBytecode.");
        if (Arrays.stream(founds).sum() == 0)
            throw new RuntimeException("No match found in trimFoundBytecode.");

        setSearchArrays(IntStream.range(0, this.searchArrays.size())
                .boxed()
                .filter(integer -> founds[integer] == 1)
                .map(integer -> this.searchArrays.get(integer))
                .collect(Collectors.toCollection(ArrayList::new)));

        setFoundBytecodePos(pos);
    }

    private int findCode(byte[] search) throws NotFoundException {
        byte[] code = this.codeAttribute.getCode();
        int i = 0;
        int j = 0;

        for(int backtrack = 0; i < code.length && j < search.length; ++i) {
            if (code[i] == search[j] || isWildBranching(search, j, code, i)) {
                if (j == 0) {
                    backtrack = i;
                }

                ++j;
                if (j == search.length) {
                    return backtrack;
                }
            } else if (j > 0) {
                i = backtrack;
                j = 0;
            }
        }

        throw new NotFoundException("code");
    }

    private boolean isWildBranching(byte[] search, int j, byte[] code, int i) {
        if (search[j] == code[i] &&
                isBranchingOpCode(search[j]) && search[j + 1] == 0 && search[j + 2] == 0)
            return true;
        else if(j > 0 && search[j - 1] == code[i - 1] &&
                isBranchingOpCode(search[j - 1]) && search[j] == 0 && search[j + 1] == 0)
            return true;
        else return j > 1 && search[j - 2] == code[i - 2] &&
                    isBranchingOpCode(search[j - 2]) && search[j - 1] == 0 && search[j] == 0;
    }

    private boolean isBranchingOpCode(byte opcode){
        return opcode == (byte)Opcode.IF_ACMPEQ || opcode == (byte)Opcode.IF_ACMPNE ||
                opcode == (byte)Opcode.IF_ICMPEQ || opcode == (byte)Opcode.IF_ICMPGE || opcode == (byte)Opcode.IF_ICMPGT
                || opcode == (byte)Opcode.IF_ICMPLE || opcode == (byte)Opcode.IF_ICMPLT ||
                opcode == (byte)Opcode.IF_ICMPNE || opcode == (byte)Opcode.IFEQ || opcode == (byte)Opcode.IFGE ||
                opcode == (byte)Opcode.IFGT || opcode == (byte)Opcode.IFLE || opcode == (byte)Opcode.IFLT ||
                opcode == (byte)Opcode.IFNE || opcode == (byte)Opcode.IFNONNULL || opcode == (byte)Opcode.IFNULL;
    }

    private int findCodeWild(byte[] search) throws NotFoundException {
        byte[] code = this.codeAttribute.getCode();
        int i = 0;
        int j = 0;

        for(int backtrack = 0; i < code.length && j < search.length; ++i) {
            if (code[i] == search[j]) {
                if (j == 0) {
                    backtrack = i;
                }

                ++j;
                if (j == search.length) {
                    return backtrack;
                }
            } else if (j > 0) {
                i = backtrack;
                j = 0;
            }
        }

        throw new NotFoundException("code");
    }

    int getTableLineNumberStart() throws RuntimeException{
        // Get the line number table entry for the bytecodeIndex.
        LineNumberAttribute lineNumberAttribute = (LineNumberAttribute) this.codeAttribute
                .getAttribute(LineNumberAttribute.tag);
        int startLineNumber = lineNumberAttribute.toLineNumber(this.foundBytecodePos);
        int lineNumberTableOrdinal =  IntStream.range(0, lineNumberAttribute.tableLength())
                .filter(value -> Objects.equals(lineNumberAttribute.lineNumber(value), startLineNumber))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        return startLineNumber;
    }

    int getTableLineNumberAfter() throws RuntimeException{
        // Get the line number table entry for the bytecodeIndex.
        LineNumberAttribute lineNumberAttribute = (LineNumberAttribute) this.codeAttribute
                .getAttribute(LineNumberAttribute.tag);
        int endLineNumber = lineNumberAttribute.toLineNumber(this.foundBytecodePos + this.searchArrays.get(0).getSize());
        int lineNumberTableOrdinal =  IntStream.range(0, lineNumberAttribute.tableLength())
                .filter(value -> Objects.equals(lineNumberAttribute.lineNumber(value), endLineNumber))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        int def = lineNumberAttribute.lineNumber(lineNumberTableOrdinal);
        int abc = lineNumberAttribute.lineNumber(lineNumberTableOrdinal + 1);
        return endLineNumber;
    }

    private Bytecode copyBytecode(Bytecode bytecode) {
        Bytecode abc = new Bytecode(this.constPool);
        byte[] def = bytecode.get();
        int[] ints = IntStream.range(0, def.length)
                .map(value -> (int)def[value])
                .toArray();
        Arrays.stream(ints)
                .forEach(abc::add);
        return abc;
    }

    private int[] lookupVariableSlot(String name, String signature) {
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute)this.codeAttribute.
                getAttribute(LocalVariableAttribute.tag);
        return IntStream.range(0, localVariableAttribute.tableLength())
                .filter(value -> Objects.equals(localVariableAttribute.variableName(value), name) &&
                        Objects.equals(localVariableAttribute.signature(value), signature))
                .map(localVariableAttribute::index)
                .toArray();
    }

    synchronized private void setSearchArrays(ArrayList<Bytecode> searchArrays) {
        this.searchArrays = searchArrays;
    }

    synchronized private void setFoundBytecodePos(int foundBytecodePos) {
        this.foundBytecodePos = foundBytecodePos;
    }
}
