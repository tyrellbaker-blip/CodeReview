/* cst 438: Assignement 2, Code Review
    Daniel Rodas

 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static class User{
        private String userName;
        private String displayName;
        private String state;
        private ArrayList<String> friends;


        User(){
            userName="----";
            displayName="----";
            state="----";
            friends.add("sss");
        }
        User(String userName, String displayName,
             String state, ArrayList<String>friends){
            this.userName=userName;
            this.displayName=displayName;
            this.state=state;
            this.friends=friends;
        }
        public String getState(){return state;}
        public String getUserName(){return userName;}
        public String getDisplayName(){return displayName;}

        @Override
        public String toString() {
            return userName+" "+displayName+" "+state;
        }
    }

    public static class Post{
        private String postID;
        private String userName;
        String visibility;

        Post(){
            postID="0000";
            userName="=====";
            visibility="public";

        }

        Post(String postID, String userName, String visibility){
            this.postID=postID;
            this.userName=userName;
            this.visibility=visibility;
        }
        public String getUserName(){return userName;}
        public String getPostID(){return postID;}
        public String getVisibility(){return visibility;}

        @Override
        public String toString() {
            return postID+" "+userName+" "+visibility;
        }
    }


    public static void readUserData(String filePath, ArrayList<User> platform ) {
        try (Scanner inputStream = new Scanner(new FileInputStream(filePath))) {

            ArrayList<User> users = new ArrayList<>();

            while (inputStream.hasNextLine()) {
                String[] userInfo = inputStream.nextLine().split(";");
                String userName = userInfo[0];
                String displayName = userInfo[1];
                String state = userInfo[2];
                String[] friendsArray = userInfo[3].substring(1, userInfo[3].length() - 1).split(",");
                ArrayList<String> friends = new ArrayList<>();
                for (String friend : friendsArray) {
                    friends.add(friend.trim());
                }
                User user = new User(userName, displayName, state, friends);
                users.add(user);
            }

//            System.out.println("=======================Inside readDataUser=================\n");
            for (User user : users) {
                platform.add(user);
//                System.out.println(user);

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }
    }

    public static void readPostData(String filePath, ArrayList<Post> page){
        try (Scanner inputStream = new Scanner(new FileInputStream(filePath))) {


            ArrayList<Post> posts = new ArrayList<>();

            while (inputStream.hasNextLine()) {
                String[] postInfo = inputStream.nextLine().split(";");
                String postID= postInfo[0];
                String userName = postInfo[1];
                String visibility = postInfo[2];

                Post p = new Post(postID,userName,visibility);
                posts.add(p);

            }

//            System.out.println("=======================Inside readPostUser=================\n");
            for (Post p: posts) {
                page.add(p);

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }



    }
    public static boolean match(ArrayList<User> user){
        boolean match= false;
        int i=0;
        for (User c: user){
            if(c.userName.equals(user.get(i).userName)){
                match=true;
            }
            i++;
        }
        return match;
    }

    // Function to clear the terminal screen








    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int input;
        boolean found= false;
        String searchPostID, searchUsername;
        ArrayList<User> platform= new ArrayList<>();
        ArrayList<String> f= new ArrayList<>();
        ArrayList<Post> page = new ArrayList<>();
//        UUID PID=UUID.randomUUID();
//        UUID UID=UUID.randomUUID();
//
//        System.out.println(PID+"\n"+UID);

//
//        f.add("EEE");

//        User example= new User("CCC","Bobby","CA",f);
//
//        platform.add(example);
//        example= new User("QQQ","qqqq","CA",f);
//        platform.add(example);
//        example= new User("EEE","eeee","CA",f);
//        platform.add(example);
//
//        for (User c: platform){
//            System.out.println(c);
//        }
//        System.out.println("\n====POSTS====\n");
//
//        Post p= new Post(0000,"CCC","public");
//        page.add(p);
//        p= new Post(0002,"BBB","public");
//        page.add(p);
//        p= new Post(0003,"CCC","true");
//        page.add(p);
//        p= new Post(0004,"CCC","public");
//        page.add(p);
//
//        for(Post c: page){
//            System.out.println(c);
//        }
//        System.out.println("\n=========SEARCH===========\n");
//        for(Post c: page){
//            if(c.getUserID().equals("CCC")){
//                System.out.println(c);
//            }
//
//        }
//        readUserData("resources/userInfo",platform);

        // seeing if stuff works ^



        do{
            System.out.println("=============Choose an option=============");
            System.out.println("1.) Load Data\n" +
                    "2.) Check Visibility\n" +
                    "3.) Retrieve posts\n" +
                    "4.) Search user by location\n" +
                    "5.) Exit");




            input=kb.nextInt();
            switch (input){
                case 1:
                    System.out.println("------DATA LOADED------");

                    readUserData("resources/user-Info",platform);
                    readPostData("resources/post-Info",page);

                    break;
                case 2:

                    System.out.println("-------Search-------\n" +
                            "Input post ID: ");
                    searchPostID=kb.next();
                    System.out.println("Input Username");
                    searchUsername=kb.next();



                    for (Post p : page) {
                        if (p.getPostID().equals(searchPostID) && p.getUserName().equals(searchUsername)) {
                            if (p.getVisibility().equals("public")) {
                                System.out.println("Access Permitted");
                            } else {
                                System.out.println("Access Denied");
                            }
                            found = true; // Set found flag to true
                            break; // Break the loop immediately
                        }
                    }

                    if (!found) {
                        System.out.println("Access Denied");
                    }
                    found=false;


                    break;
                case 3:

                    System.out.println("-------Retrieve posts-------\n" +
                            "Input Username: ");
                    searchUsername=kb.next();
                    for(Post p: page){
                        if(p.getVisibility().equals("public")&&p.getPostID().equals(searchUsername)){
                            System.out.println(p.getPostID());
                        }
                    }

                    break;
                case 4:
                    System.out.println("-------Search users by Location-------\n" +
                            "Input location: (initals)");
                    searchUsername=kb.next();

                    for(User u: platform){
                        if(u.getState().equals(searchUsername)){
                            System.out.println(u.getDisplayName());
                        }
                    }

                    break;
                case 5:

                    System.out.println("Bye!");

                    break;
                default:

                    System.out.println("whoops! "+input+", is an  invalid input!");
                    break;

            }

        }while(input!=5);



    }
}