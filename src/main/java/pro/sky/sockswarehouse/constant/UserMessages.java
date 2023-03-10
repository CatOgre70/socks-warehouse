package pro.sky.sockswarehouse.constant;

public enum UserMessages {

    SOCKS_COLOR_NOT_FOUND_EXCEPTION("Color \"%color%\" was not found in the Socks Color Table, please" +
            "contact your system administrator to correct the application"),
    SOCKS_COTTON_PART_WRONG_NUMBER_EXCEPTION("Cotton part should be integer value from 0 to 100 " +
            "per cent inclusively"),
    SOCKS_QUANTITY_WRONG_NUMBER_EXCEPTION("Socks quantity should be more than 0"),
    INVOICE_SAVE_ERROR("Invoice didn't save in the database! Potential data integrity threat"),
    INVOICE_SAVE_SUCCESS("Invoice saved in the database successfully"),
    USER_NOT_FOUND_EXCEPTION("User with %id% was not found in the database"),
    SOCKS_NOT_FOUND_OR_NOT_ENOUGH_QUANTITY("Such socks was not found or have not enough quantity");

    public final String msgText;

    UserMessages(String msgText){
        this.msgText = msgText;
    }


}
