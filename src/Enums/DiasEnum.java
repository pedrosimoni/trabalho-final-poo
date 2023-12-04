package Enums;

public enum DiasEnum {
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO,
    DOMINGO;

    public DiasEnum next(){
       if(this.equals(DOMINGO)){
           return SEGUNDA;
       }else{
           return values()[ordinal() + 1];
       }
    }
}
