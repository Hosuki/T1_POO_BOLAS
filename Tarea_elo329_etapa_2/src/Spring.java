public class Spring extends PhysicsElement {
   private static int id=0;  // Spring identification
   protected final double restLength;
   private final double stiffness;
   protected Ball a_end, b_end;
   
   
   private Spring(){   // nobody can create a block without state
      this(0,0);
   }
   public Spring(double restLength, double stiffness){
      super(id++);
      this.restLength = restLength;
      this.stiffness = stiffness;
      a_end = b_end = null;
   }
   public void attachEnd (Ball sa) {  // note: we attach a spring to a ball, 
      if(a_end==null){                             //       not the other way around.
         a_end = sa;
         sa.attachSpring(this);
      	 return;
      }
      // to be completed by you
      if(b_end==null){  //
     	 b_end = sa;   //
      	 sa.attachSpring(this);
      	 return;
      }
   }
   public double getAendPosition() {
      if (a_end != null)
         return a_end.getPosition();
      if (b_end != null)
         return b_end.getPosition()-restLength;
      return 0;
   }
   public double getBendPosition() {
    // to be coded by you
	   if (b_end != null)
	         return b_end.getPosition();
	   if (a_end != null)
	         return a_end.getPosition()-restLength;
	   return 0;
   }
   public double getForce(Ball ball) {
      double force = 0;
      if ((a_end == null) || (b_end == null))
         return force;
      if ((ball != a_end) && (ball != b_end))
         return force;
      // to be completed by you
      if((ball == a_end)){
    	  force = stiffness * getAendPosition();
    	  return force;
      }
      if((ball == b_end)){
    	  force = stiffness * getBendPosition();
    	  return force;
      }
      return force;
   }
   public void computeNextState(double delta_t, MyWorld w){
	   if(a_end != null){
		   a_end.aceleration_tPlusDelta = a_end.getAceleration(this);
		   a_end.speed_tPlusDelta = a_end.getSpeed() + (3 * a_end.aceleration_tPlusDelta - a_end.aceleration)* delta_t;
		   a_end.pos_tPlusDelta = a_end.getPosition() + (a_end.getSpeed()* delta_t) + ((4 * a_end.aceleration_tPlusDelta - a_end.aceleration) / 6)* (delta_t*delta_t);
	   }
	   if(b_end != null){
		   b_end.aceleration_tPlusDelta = b_end.getAceleration(this);
		   b_end.speed_tPlusDelta = b_end.getSpeed() + (3 * b_end.aceleration_tPlusDelta - b_end.aceleration)* delta_t;
		   b_end.pos_tPlusDelta = b_end.getPosition() + (b_end.getSpeed()* delta_t) + ((4 * b_end.aceleration_tPlusDelta - b_end.aceleration) / 6)* (delta_t*delta_t);
		   
	   }
	   
   } 
   public void updateState(){
	   if(a_end != null){
	   a_end.aceleration = a_end.aceleration_tPlusDelta;
	   a_end.speed_t = a_end.speed_tPlusDelta;
	   a_end.pos_t = a_end.pos_tPlusDelta;
	   }
	   if(b_end != null){
	   b_end.aceleration = b_end.aceleration_tPlusDelta;
	   b_end.speed_t = b_end.speed_tPlusDelta;
	   b_end.pos_t = b_end.pos_tPlusDelta;
	   }
   }

   public String getDescription() {
      return "Spring_"+ getId()+":a_end\tb_end";
   }
   public String getState() {
     //  to be coded by you
	 String State;
	 String PositionA = String.valueOf (this.getAendPosition());
	 String PositionB = String.valueOf (this.getBendPosition());
	 State = (PositionA + "\\" + PositionB);
	 return (State);
   }
}
