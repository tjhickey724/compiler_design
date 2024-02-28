
class mj6aa{
    public static void main(String[] args){
        System.out.println((new Demo()).go(10,3));
}}




class Demo {

        int  c;
        int[]  log;
        boolean  debugging;
        Demo  d;

        public int  incr(int  num ){
            System.out.println(c);
            c = c+num ;

            return(c);


        }



        public int  go(int  num ,int  v ){

            int  tmp;
            int  c;
            boolean  e;
            int  f;            f = v+v*v ;
            tmp = f+num*num ;
            e = false ;
            d = new Demo() ;
            e = e&&e&&e&&e&&(f+f)*d*f+f<f ;
            System.out.println(e);

            return(this.incr(tmp));


        }


}
