package pro.sky.sockswarehouse.constant;

import java.util.HashMap;
import java.util.Map;

public enum Operation {

    MORE_THAN("moreThan"),
    LESS_THAN("lessThan"),
    EQUAL("equal");

    private final String operationName;

    Operation(String operationName) {
        this.operationName = operationName;
    }

    private static final Map<String, Operation> OPERATION_BY_NAME = new HashMap<>();

    static {
        for(Operation o: values()){
            OPERATION_BY_NAME.put(o.operationName, o);
        }
    }

    /**
     * Method to get operation by String with the name
     * @param name - String with operation name
     * @return Operation constant with defined operation name or null if element with defined color name was not found
     */
    public static Operation getOperationByName(String name){
        return OPERATION_BY_NAME.get(name);
    }


}
