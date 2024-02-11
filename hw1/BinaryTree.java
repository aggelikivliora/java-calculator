package ce326.hw1;

public class BinaryTree {
    node root;
    
    public BinaryTree(node root){
        this.root = root;
    }
   
    public int FindOperand(String str){
        int j=0;
        int k=1;
        int pos=0;
        int start=0,end=str.length()-1;
        char curr;

        int parenthesis=0;
        int closeParenthesis=0;
        if(str.charAt(0)=='('){
            for(int i=start; i<=end; i++){
                curr = str.charAt(i);
                if(curr=='('){parenthesis++;}
                if(curr==')'){parenthesis--;}
                if(parenthesis==0){closeParenthesis++;}
            }
        }
        if((closeParenthesis==1)&&(str.charAt(start)=='(') && (str.charAt(end)==')')){
            start++;
            end--;
        }

        for(int i=start; i<end; i++){
            curr = str.charAt(i);
            if(curr=='('){j++;}
            if(curr==')'){j--;}
            
            if((j==0) && ((curr=='^') || (curr=='*') || (curr=='/') || (curr=='x') || (curr=='+') || (curr=='-'))){
                if((curr=='^') && (k==1)){
                    k = 1;
                    pos = i;
                }
                else if(((curr=='*') || (curr=='/') || (curr=='x')) && (k<=2)){
                    k = 2;
                    pos = i;
                }
                else if(((curr=='+') || (curr=='-')) && (k<=3)){
                    k = 3;
                    pos = i;
                }
            }
        }
        
        return pos;
    }
    
    public node BuildTree(String string, node root){
        this.root = root;
        int j=0;
        char curr;
        for(int i=0; i<string.length(); i++){
            curr = string.charAt(i);
            if((curr=='^') || (curr=='*') || (curr=='/') || (curr=='x') || (curr=='+') || (curr=='-')){
                j++;
                break;
            }
        }
        
        if(j == 0){
            if((string.charAt(0)=='(')&&(string.charAt(string.length()-1)==')')){
                string = string.substring(1, string.length()-1);
                root.str = string;
            }
            return root; 
        }
        
        int position = FindOperand(string);
        int midLimitR = position+1;
        int midLimitL = position;
        int rightLimit = string.length()-1;
        int leftLimit = 0; 
        StringBuilder str = new StringBuilder(string);
        
        // AN YPARXOUN PAREN8ESEIS STHN ARXH KAI TO TELOS
        int parenthesis=0;
        int closeParenthesis=0;
        if(str.charAt(0)=='('){
            for(int i=leftLimit; i<=rightLimit; i++){
                curr = str.charAt(i);
                if(curr=='('){parenthesis++;}
                if(curr==')'){parenthesis--;}
                if(parenthesis==0){closeParenthesis++;}
            }
        }
        int par=1;
        while((closeParenthesis==par)&&(string.charAt(leftLimit)=='(') && (string.charAt(rightLimit) == ')')){
            leftLimit++;
            rightLimit--;
            par++;
        }
        
        String strLeft = str.substring(leftLimit, midLimitL);
        String strRight = str.substring(midLimitR,rightLimit+1);
        root.str = str.substring(midLimitL,midLimitR); 
      
        node currL = new node(strLeft);
        root.left = BuildTree(strLeft, currL);
        node currR = new node(strRight);
        root.right = BuildTree(strRight, currR);
        return root;
    }
    
    
    public String postorder(node curr, StringBuilder str) {

        if(curr.left!=null){
            postorder(curr.left, str);
            str.append(curr.left.str);
        }
        
        if(curr.right!=null){
            postorder(curr.right, str);
            str.append(curr.right.str);
        }
        
        if(curr != root){
            str.append(" ");
        }
        if(curr == root){
            str.append(" ");
            str.append(curr.str);
        }
        
        return str.toString();
    }
    
    
    
    public String preorder(node curr, StringBuilder str) {
        if(curr==root){
            str.append(curr.str);
            str.append(" ");
        }
        if(curr.left!=null){
            str.append(curr.left.str);
            str.append(" ");
            preorder(curr.left, str);
        }
        
        if((curr.right!=null)&&(curr!=root.right)){
            str.append(curr.right.str);
            str.append(" ");
            preorder(curr.right, str);
        }
        if(curr==root.right){
            str.append(curr.right.str);
        }

        return str.toString();
    }

    
    
    public static double inorder(node curr) {
        if (!("*".equals(curr.str) || "/".equals(curr.str) || "+".equals(curr.str) || "-".equals(curr.str) || "^".equals(curr.str) || "x".equals(curr.str))){
            double d=Double.parseDouble(curr.str);
            return d;
        }
        double rt;
        double lft;
        lft=inorder(curr.left);
        rt=inorder(curr.right);
        double result;
        if("+".equals(curr.str)){
            result = rt+lft;
            return result;
        }
        else if("-".equals(curr.str)){
            result = lft-rt;
            return result;
        }
        else if("*".equals(curr.str) || "x".equals(curr.str)){
            result = rt*lft;
            return result;
        }
        else if("/".equals(curr.str)){
            result = lft/rt;
            return result;
        }
        else if("^".equals(curr.str)){
            result = Math.pow(lft,rt);
            return result;
        }
        return 0;
    }
    
}
