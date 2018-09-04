public class Domino {
    private String top;
    private String bottom;
    private String name;
    private String state;
    private Boolean prefixFlag;
    public Domino(){
        prefixFlag = false;
    }
    public Domino(String t, String b, String n){
        top = t;
        bottom = b;
        name = n;
        setState();
    }
    private void setState(){
        if(top.startsWith(bottom)){
            state = top.substring(bottom.length(),top.length())+"/";
            prefixFlag =true;
        }else if(bottom.startsWith(top)){
            state = "/" + bottom.substring(top.length(),bottom.length());
            prefixFlag =true;
        }else{
            prefixFlag =false;
        }
    }
    public String getState(){
        return state;
    }
    public Boolean getPrefixFlag(){
        return prefixFlag;
    }
    public String getTop(){
        return top;
    }
    public String getBottom(){
        return bottom;
    }
    public String getName(){
        return name;
    }
    public void setTop(String t){
        top = t;
    }
    public void setBottom(String b){
        bottom = b;
    }
    public void setName(String n){
        name = n;
    }
    public void Duplicate(Domino d){
        top = d.getTop();
        bottom = d.getBottom();
        name = d.getName();
    }
}
