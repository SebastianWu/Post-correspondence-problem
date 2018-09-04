import java.util.*;

public class test {
    static Set<Domino> DominoSet = new LinkedHashSet<>();
    static int flag_no_solution;
    static int flag_no_solutionWL;
    static ArrayList<Domino> States = new ArrayList<>();
    static int Machine_max_Quesize = 512;

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.print("type Maximun Queue Size of Breadth First Search: ");
        int read = reader.nextInt();
        while(read > Machine_max_Quesize){
            System.out.print("Max queue size exceeds maximum machine memory size (512). \n");
            System.out.print("type Maximun Queue Size of Breadth First Search: ");
            read = reader.nextInt();
        }
        int maxQueSize = read;
        System.out.print("type Maximun Depth of Breadth First Search and Iterative Deepening Search: ");
        int maxDepth = reader.nextInt();
        String cont = "";
        int domino_num = 1;
        while(!cont.equalsIgnoreCase( "N")){
            String Name = "D"+ domino_num;
            System.out.print("type Domino Top: ");
            String Top = reader.next();
            System.out.print("type Domino Bottom: ");
            String Bottom = reader.next();
            Domino D = new Domino(Top, Bottom, Name);
            DominoSet.add(D);
            System.out.print("type continue add Domino( Y/N ): ");
            cont = reader.next();
            while(!cont.equalsIgnoreCase("N")&&!cont.equalsIgnoreCase("Y")){
                System.out.print("Please type Y to continue adding Domino or type N to stop adding Domino: ");
                cont = reader.next();
            }
            domino_num ++;
        }
        System.out.print("If you want to output the state space please type 1 or else type 0. \n");
        System.out.print("Please indicate output type: ");
        cont = reader.next();
        while(!cont.equalsIgnoreCase("1")&&!cont.equalsIgnoreCase("0")){
            System.out.print("Please type 1 to indicate output state space, or else type 0. \n");
            System.out.print("Please indicate output type: ");
            cont = reader.next();
        }
        String output_flag = cont;
        reader.close();
        System.out.print("\n");

        /*
        Domino D1 = new Domino("c","cca", "D1");
        Domino D2 = new Domino("ac", "ba", "D2");
        Domino D3 = new Domino("bb", "b", "D3");
        Domino D4 = new Domino("ac", "cb", "D4");

        DominoSet.add(D1);
        DominoSet.add(D2);
        DominoSet.add(D3);
        DominoSet.add(D4);
        */

        //Breadth First Search with Clever States :
        System.out.print("Breadth First Search with Clever States : \n");
        Domino cleverBFSsolution = cleverBFS(maxQueSize, maxDepth);
        if(output_flag.equalsIgnoreCase("1")) {
            System.out.print("States Space:\n");
            printStates();
        }
        System.out.print("\n");
        if(flag_no_solution == 1){
            if(flag_no_solutionWL == 1){
                System.out.print("No Solution Found within the limit of search! \n");
            }else {
                System.out.print("No Solution Found!\n");
            }
        }else {
            System.out.print("Solution Found! \n");
            System.out.print(cleverBFSsolution.getName() + " : " + cleverBFSsolution.getTop() + "\n\n");
        }
        //Domino DLSsolution = DLSdriver(5);
        //System.out.print(DLSsolution.getName()+" : "+DLSsolution.getTop() + "\n\n");

        //Iterative Deepening Search with Clever States
        System.out.print("\n");
        //initialize flags & States
        flag_no_solution = 0;
        flag_no_solutionWL = 0;
        States.clear();
        System.out.print("Iterative Deepening Search with Clever States : \n");
        Domino IDSsolution = IterativeDeepeningSearch(maxDepth);
        if(output_flag.equalsIgnoreCase("1")) {
            System.out.print("States Space:\n");
            printStates();
        }
        System.out.print("\n");
        if(flag_no_solution == 1){
            System.out.print("No Solution Found! \n");
        }else if(flag_no_solutionWL == 1){
            System.out.print("No Solution Found within the limit of search! \n");
        }else{
            System.out.print("Solution Found! \n");
            System.out.print(IDSsolution.getName() + " : " + IDSsolution.getTop() + "\n\n");
        }

    }


    static Domino cleverBFS(int maxQueSize, int maximumDepth){
        Queue<Domino> DominoQueue = new LinkedList<>();
        Hashtable<String, Domino> SeenQueue = new Hashtable<>();
        int depth = 1;
        for(Domino d : DominoSet){
            if(d.getPrefixFlag()){
                DominoQueue.add(d);
                SeenQueue.put(d.getState(),d);
            }
        }

        while(!DominoQueue.isEmpty()){
            Domino s = DominoQueue.poll();
            if(DominoQueue.size()<maxQueSize || DominoQueue.size()==maxQueSize ) {
                /*for(Domino d : DominoQueue){
                    System.out.print(d.getName() + "|");
                }
                System.out.print("\n");*/

                //System.out.print(s.getState()+ " ");
                //System.out.print(s.getName() + " " + s.getTop() + "/" + s.getBottom() + "\n");
                States.add(s);
                if (s.getTop().equals(s.getBottom())) {
                    return s;
                }else {
                    int flag_treedepth_add = 0;
                    for (Domino d : DominoSet) {
                        Domino temp = new Domino(s.getTop() + d.getTop(), s.getBottom() + d.getBottom(), s.getName() + ", " + d.getName());
                        if(temp.getPrefixFlag()&& !SeenQueue.containsKey(temp.getState())){
                            DominoQueue.add(temp);
                            SeenQueue.put(temp.getState(),temp);
                            flag_treedepth_add = 1;
                        }
                    }
                    if(flag_treedepth_add == 1){
                        depth = depth + 1;
                    }
                }
                if(depth > maximumDepth) {
                    flag_no_solutionWL = 1;
                    break;
                }
            }else {
                flag_no_solutionWL = 1;
                break;
            }
        }
        flag_no_solution = 1;
        return new Domino();
    }

    static Domino DLSdriver(int maximumDepth){
        Domino Result = new Domino();
        Hashtable<String, Domino> SeenState = new Hashtable<>();
        flag_no_solution = 1;
        for(Domino d : DominoSet){
            if(d.getPrefixFlag()){
                Domino temp = DLS(d, maximumDepth, new Domino(), SeenState);
                if(temp!=null) {
                    Result.Duplicate(temp);
                }
            }
        }
        return Result;
    }

    static int depth = 0;
    static Domino DLS(Domino s, int maximumDepth, Domino result, Hashtable<String, Domino> SeenState){
        int treeDepth = 1;
        if(depth<maximumDepth+1) {
            if (result.getName() == null) {
                SeenState.put(s.getState(),s);
                depth++;
                //System.out.print(s.getState()+ " ");
                //System.out.print(s.getName() + " " + s.getTop() + "/" + s.getBottom() + "\n");
                States.add(s);
                if (s.getTop().equals(s.getBottom())) {
                    result.Duplicate(s);
                } else {
                    for (Domino d : DominoSet) {
                        Domino temp = new Domino(s.getTop() + d.getTop(), s.getBottom() + d.getBottom(), s.getName() + ", " + d.getName());
                        if(temp.getPrefixFlag()&&!SeenState.containsKey(temp.getState())) {
                            result.Duplicate(DLS(temp, maximumDepth, result, SeenState));
                        }
                    }
                }
                treeDepth = depth;
                depth--;
            }
        }
        //System.out.print(treeDepth+" "+(maximumDepth+1)+"\n");
        if(treeDepth == maximumDepth+1){
            flag_no_solution = 0;
        }
        return result;
    }


    static Domino IterativeDeepeningSearch(int maximunDepth){
        int Threshold = maximunDepth;
        for( int depth  = 0; depth < Threshold; depth ++){
            Domino result = DLSdriver(depth);
            if (result.getName() != null) {
                //System.out.print(result.getName());
                return result;
            }
            if(result.getName() == null && flag_no_solution ==1){
                break;
            }
        }
        flag_no_solutionWL = 1;
        return new Domino();
    }

    static void printStates(){
        for(Domino d : States){
            System.out.print(d.getName() + " " + d.getState()+"\n");
        }
    }
}
