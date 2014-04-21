import java.util.*;

public class Ball extends PhysicsElement {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   public double pos_t;     // current position at time t
   public double pos_tPlusDelta;  // next position in delta time in future
   public double speed_t;   // speed at time t
   public double speed_tPlusDelta;   // speed in delta time in future
   public double aceleration = 0; //current aceleration at time t.
   public double aceleration_tPlusDelta = 0; // next aceleration in delta time in future.
   
   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      this.radius = radius;
      pos_t = position;
      speed_t = speed;
   }
   public double getRadius() {
      return radius;
   }
   public double getSpeed() {
      return speed_t;
   }
   public double getMass(){
	  return mass;
   }
   public double getPosition(){
	  return pos_t;
   }
   public double getAceleration(Spring Myspring){
	  double aceleration;
	  aceleration = Myspring.getForce(this)/getMass();
	  return (aceleration);
   }
   
   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
        speed_tPlusDelta = (speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t;
     } else {
        speed_tPlusDelta = speed_t;
        pos_tPlusDelta = pos_t + speed_t*delta_t;
     }
   }
   public boolean collide(Ball b) {
      //a cada bola le pregunto a traves de su metodo collide si esta colisionando
	   // con alguna otra bola que le paso como argumento Ball b
      boolean estanCerca = Math.abs(this.pos_t - b.pos_t) <= (this.radius + b.radius);
      boolean seAcercan;
      if (getPosition() < b.getPosition())
            seAcercan = (b.getSpeed()-getSpeed()) <0;
       else
            seAcercan = (b.getSpeed()-getSpeed()) > 0;
       return estanCerca && seAcercan;
/*      
	   if ( ){ //es la distancia entre las bolas igual a la suma de los radios? Math.abs(this.pos_t - b.pos_t) <= (this.radius + b.radius)
		   // si es asi entonces las bolas estan chocando
		   return true;// entonces retorna verdadero.
	   }
	   else return false;
*/
    }
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   
   public String getDescription() {
   // to be coded by you
   // FixedHook_0:x    Spring_0:a_end    b_end    Ball_0:x    Ball_1:x    
	   String Title; //titulo de la tabla de datos.
	   Title = "Ball_" + super.getId() + ":x";
	   return(Title);
   }
   public String getState(){
	   String State;
	   State = String.valueOf(this.getPosition());
	   return (State);
	   
   }
   
   public void attachSpring (Spring Myspring){
	   if (this == Myspring.a_end){
		   this.pos_t = Myspring.getAendPosition();
	   }
	   if (this == Myspring.b_end){
		   this.pos_t = Myspring.getBendPosition();
	   }
   }
   
}
