package com.joedobo27.libs.bytecode;

class NoMatchingConstPoolIndexException extends RuntimeException{
    NoMatchingConstPoolIndexException(String message) {
        super(message);
    }
}
