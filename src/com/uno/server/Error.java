package com.uno.server;

public enum Error{
    E01("This name is not unique"),
    E02("This command does not exist"),
    E03("This move is invalid"),
    E04("This command is invalid"),
    E05("You don’t have the card"),
    E06("There are currently not enough players, wait for at least two players to connect"),
    E07("It is not your turn"),
    E08("General error"),
    E09("custom error");

    private String errorDescription;

    /**
     * A method that returns the description of the error
     * @return the error description
     */
    public String getErrorDescription(){
        return this.errorDescription;
    }

    /**
     * Sets the error description
     * @param errorDescription the description of the error
     * @requires errorDescription !=null
     * @ensures this.errorDescription is set to the errorDescription
     */
    Error(String errorDescription){
        this.errorDescription = errorDescription;
    }

}