package com.joedobo27.libs.bml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


@SuppressWarnings({"WeakerAccess", "unused"})
public class BmlForm {
    private static final Logger logger;
    private final StringBuffer buf = new StringBuffer();
    private static final String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";

    private int openBorders = 0;
    private int openCenters = 0;
    private int openVarrays = 0;
    private int openScrolls = 0;
    private int openHarrays = 0;

    private int indentNum = 0;
    @SuppressWarnings("CanBeFinal")
    private boolean beautify = false;

    private boolean closeDefault = false;

    static {
        logger = Logger.getLogger(BmlForm.class.getName());
    }

    public BmlForm(String formTitle) {
        beginBorder();
        beginCenter();
        addBoldText(formTitle);
        endCenter();

        beginScroll();
        beginVerticalFlow();

        closeDefault = true;        // in toString() we close the opened: varray, scroll, border
    }

    public BmlForm(String formTitle, int width, int height) {
        beginBorder(width, height);
        beginCenter();
        addBoldText(formTitle);
        endCenter();

        beginScroll();
        beginVerticalFlow();
        buf.append(";");

        closeDefault = true;        // in toString() we close the opened: varray, scroll, border
    }


    public void beginBorder() {
        buf.append(indent("border{"));
        indentNum++;
        openBorders++;
    }

    public void beginBorder(int width, int height) {
        buf.append(indent("border{"));
        setSizeAttribute(width, height);
        indentNum++;
        openBorders++;
    }

    public void endBorder() {
        indentNum--;
        buf.append(indent("}"));
        openBorders--;
    }

    public void beginCenter() {
        buf.append(indent("center{"));
        indentNum++;
        openCenters++;
    }

    public void endCenter() {
        indentNum--;
        buf.append(indent("};null;"));
        openCenters--;
    }

    public void beginVerticalFlow() {
        buf.append(indent("varray{rescale=\"true\";"));
        indentNum++;
        openVarrays++;
    }

    public void endVerticalFlow() {
        indentNum--;
        buf.append(indent("}"));
        openVarrays--;
    }

    public void beginScroll() {
        buf.append(indent("scroll{vertical=\"true\";horizontal=\"false\";"));
        indentNum++;
        openScrolls++;
    }

    public void endScroll() {
        indentNum--;
        buf.append(indent("};null;null;"));
        openScrolls--;
    }

    public void beginHorizontalFlow() {
        buf.append(indent("harray {"));
        indentNum++;
        openHarrays++;
    }

    public void endHorizontalFlow() {
        indentNum--;
        buf.append(indent("}"));
        openHarrays--;
    }

    public void addBoldText(String text, String... args) {
        addText(text, "bold", args);
    }

    public void addHidden(String name, String val) {
        buf.append(indent("passthrough{id=\"" + name + "\";text=\"" + val + "\"}"));
        //passthrough{id="id";text="" + this.id + ""}
    }

    public void addText(String text, String... args) {
        addText(text, "", args);
    }

    public String indent(String s) {
        return (beautify ? getIndentation() + s + "\r\n" : s);
    }

    public String getIndentation() {
        if (indentNum > 0) {
            return tabs.substring(0, indentNum);
        }
        return "";
    }

    public void addRaw(String s) {
        buf.append(s);
    }

    public void addText(String text, String type, String... args) {
        String[] lines = text.split("\\r\\n");

        for (String l : lines) {
            if (beautify) {
                buf.append(getIndentation());
            }

            buf.append("text{");
            if (!type.equals("")) {
                buf.append("type='").append(type).append("';");
            }
            buf.append("text=\"");

            buf.append(String.format(l, (Object[]) args));
            buf.append("\"}");

            if (beautify) {
                buf.append("\r\n");
            }
        }
    }

    public void addButton(String name, String id) {
        buf.append(indent("button{text='" + name + "';id='" + id + "'}"));
    }

    public void addInput(@NotNull String id, @Nullable String defaultText, @Nullable Integer maxChar) {

        buf.append("input{id=\"").append(id).append("\"");
        if (defaultText != null) {
            buf.append(";text=\"").append(defaultText).append("\"");
        }
        if (maxChar != null) {
            buf.append(";maxchars=\"").append(maxChar).append("\"");
        }
        buf.append("};");
    }

    public void addLargeInput(@NotNull String id, @Nullable String defaultText, @Nullable Integer maxChar, @Nullable String bgColor,
    @Nullable Integer maxLines) {
        buf.append("input{id=\"").append(id).append("\"");
        if (defaultText != null) {
            buf.append(";text=\"").append(defaultText).append("\"");
        }
        if (maxChar != null) {
            buf.append(";maxchars=\"").append(maxChar).append("\"");
        }
        if (bgColor != null) {
            buf.append(";bgcolor=\"").append(bgColor).append("\";");
        }
        if (maxLines != null) {
            buf.append(";maxlines=\"").append(maxLines).append("\";");
        }
        buf.append("};");
    }


    public void addCheckBox(@NotNull String id, @NotNull Boolean selected ) {
        buf.append("checkbox{id=\"").append(id).append("\";");
        buf.append("selected=\"").append(Boolean.toString(selected)).append("\"};");
    }

    public void addLabel(@NotNull String text) {
        buf.append("label{text=\"").append(text).append("\"};");
    }

    public void setSizeAttribute(int width, int height) {
        buf.append(String.format("size=\"%1$s,%2$s\"", width, height));
    }

    public void beginTable(int rows, int columns, String columnLabels){
        int indexWalker = 0;
        int columnLabelCount = -1;
        int index;
        do {
            indexWalker = columnLabels.indexOf("};", indexWalker);
            if (indexWalker != -1)
                indexWalker++;
            columnLabelCount++;
        } while (columnLabelCount < columns && indexWalker != -1);
        if (columnLabelCount != columns){
            throw new IllegalArgumentException("The number of columns does not match the number of columnLabels.");
        }
        indentNum++;
        buf.append("table{rows=\"").append(rows).append("\";cols=\"").append(columns).append("\";").append(columnLabels);
    }

    public void endTable(){
        indentNum--;
        buf.append("};");
    }

    public void addDropDownList(String id, Integer defaultPos, String... options) {
        buf.append("dropdown{");
        if (id == null || Objects.equals("", id))
            throw new BMLException("The \"id\" arg can not be null or empty.");
        buf.append("id=\"").append(id).append("\";");
        if (defaultPos != null)
            buf.append("default=\"").append(defaultPos).append("\";");
        else
            buf.append("default=\"0\";");
        String[] optionString = options.clone();
        if (optionString.length == 0)
            buf.append("options=\"none\";");
        buf.append("options=\"").append(optionString[0]);
        String[] optionString2 = new String[optionString.length - 1];
        System.arraycopy(optionString, 1, optionString2, 0, optionString.length - 1);
        Arrays.stream(optionString2)
                .forEach(s -> buf.append(",").append(s));
        buf.append("\"};");
    }

    public void addRadioButton(String groupingId, String nodeId, String displayText, String hoverText, boolean isSelected, boolean isEnabled) {
        buf.append("radio{");
        if (groupingId == null || Objects.equals("", groupingId))
            throw new BMLException("The \"groupingId\" arg can not be null or empty.");
        buf.append("group=\"").append(groupingId).append("\";");
        if (nodeId == null || Objects.equals("", nodeId))
            throw new BMLException("The \"nodeId\" arg can not be null or empty.");
        buf.append("id=\"").append(nodeId).append("\";");
        if (displayText == null)
            displayText = "";
        buf.append("text=\"").append(displayText).append("\";");
        if (hoverText != null && hoverText.length() > 0)
            buf.append("hover=\"").append(hoverText).append("\";");
        buf.append("selected=\"").append(isSelected).append("\";");
        buf.append("enabled=\"").append(isEnabled).append("\";");
        buf.append("};");
    }

    public String toString() {
        if(closeDefault) {
            endVerticalFlow();
            endScroll();
            endBorder();
            closeDefault = false;
        }

        if(openCenters != 0 || openVarrays != 0 || openScrolls != 0 || openHarrays != 0 || openBorders != 0) {
            logger.log(Level.SEVERE, "While finalizing BML unclosed (or too many closed) blocks were found (this will likely mean the BML will not work!):"
                    + " center: " + openCenters
                    + " vert-flows: " + openVarrays
                    + " scroll: " + openScrolls
                    + " horiz-flows: " + openHarrays
                    + " border: " + openBorders
            );
        }

        return buf.toString();
    }

}
