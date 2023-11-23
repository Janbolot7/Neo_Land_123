package kg.neobis.neo_land.exception;

public class RecordNotFoundException extends  RuntimeException{
    public RecordNotFoundException(String message){
        super(message);
    }
}
