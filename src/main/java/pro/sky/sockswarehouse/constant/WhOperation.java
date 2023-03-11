package pro.sky.sockswarehouse.constant;

import java.util.HashMap;
import java.util.Map;

public enum WhOperation {

    INCOME("Income"),
    OUTCOME("Outcome");

    public final String operationName;

    WhOperation(String opName){
        this.operationName = opName;
    }

    private final static Map<String, WhOperation> WH_OPERATION_BY_NAME = new HashMap<>();

    static {
        for(WhOperation whOperation : values()){
            WH_OPERATION_BY_NAME.put(whOperation.operationName, whOperation);
        }
    }

    public static WhOperation getWhOperationByName(String whOperationName){
        return WH_OPERATION_BY_NAME.get(whOperationName);
    }

}
