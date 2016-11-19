
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

    //***************************************************************************************************************************************************

// import(s)

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public class Benchmark
{
  //=================================================================================================================================================

  private static final int LOCAL = 0 ;
  private static final int RMI   = 1 ;

  private static final int M1    = 0 ;
  private static final int M2    = 1 ;
  private static final int M3    = 2 ;
  private static final int M4    = 3 ;

  private static final double [][] timings = new double [ RMI + 1 ][ M4 + 1 ] ;  // In nanoseconds

  //=================================================================================================================================================

  private static double averageExecutionTimeInNanoSeconds ( Runnable r , int numberOfRepetitions )
  {
    long   startTime        ;
    long   finishTime       ;
    double totalTime  = 0.0 ;

    for ( int i = 0 ; i < numberOfRepetitions ; i++ )
    {
      startTime  = System.nanoTime() ;    r.run() ;    finishTime = System.nanoTime() ;

      totalTime += ( finishTime - startTime ) ;
    }

    return ( totalTime / numberOfRepetitions ) ;
  }

  //=================================================================================================================================================

  public static void benchmark ( LocalTargetInterface localTarget , RMITargetInterface rmiTarget , int numberOfRepetitions )
  {
    //-----------------------------------------------------------------------------------------------------------------------------------------------
      
      Runnable run1 = new Runnable(){
          @Override
          public void run() {
          localTarget.m1();
          }
          
      };
      System.out.println("Test 1 finished.") ; 
      timings[LOCAL][M1] = averageExecutionTimeInNanoSeconds(run1, numberOfRepetitions) ; 
      Runnable run2 = new Runnable(){
          @Override
          public void run() {
          localTarget.m2(900);
          }
          
      };
      System.out.println("Test 2 finished.") ; 
      timings[LOCAL][M2] = averageExecutionTimeInNanoSeconds(run2, numberOfRepetitions) ; 
      Runnable run3 = new Runnable(){
          @Override
          public void run() {
          localTarget.m3();
          }
          
      };
      System.out.println("Test 3 finished.") ; 
      timings[LOCAL][M3] = averageExecutionTimeInNanoSeconds(run3, numberOfRepetitions) ; 
      Runnable run4 = new Runnable(){
          @Override
          public void run() {
          localTarget.m4(1);
          }
          
      };

      timings[LOCAL][M4] = averageExecutionTimeInNanoSeconds(run4, numberOfRepetitions) ; 
      System.out.println("Test 4 finished.") ; 
      Runnable run5 = new Runnable(){
          @Override
          public void run() {
              try {
                  rmiTarget.m1();
              } catch (RemoteException ex) {
                  Logger.getLogger(Benchmark.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
      };
      System.out.println("Test 5 finished.") ; 
      timings[RMI][M1] = averageExecutionTimeInNanoSeconds(run5, numberOfRepetitions) ; 
      Runnable run6 = new Runnable(){
          @Override
          public void run() {
              try {
                  rmiTarget.m2(900);
              } catch (RemoteException ex) {
                  Logger.getLogger(Benchmark.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
      };
      System.out.println("Test 6 finished.") ; 
      timings[RMI][M2] = averageExecutionTimeInNanoSeconds(run6, numberOfRepetitions) ; 
      Runnable run7 = new Runnable(){
          @Override
          public void run() {
              try {
                  rmiTarget.m3();
              } catch (RemoteException ex) {
                  Logger.getLogger(Benchmark.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
      };
      System.out.println("Test 7 finished.") ; 
      timings[RMI][M3] = averageExecutionTimeInNanoSeconds(run7, numberOfRepetitions) ; 
      Runnable run8 = new Runnable(){
          @Override
          public void run() {
              try {
                  rmiTarget.m4(1);
              } catch (RemoteException ex) {
                  Logger.getLogger(Benchmark.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
          
      };
      System.out.println("Test 8 finished.") ; 
      timings[RMI][M4] = averageExecutionTimeInNanoSeconds(run8, numberOfRepetitions) ; 
    // ... (Fill in the "timings" array by making use of the "averageExecutionTimeInNanoSeconds" method)

    //-----------------------------------------------------------------------------------------------------------------------------------------------
  }

  //=================================================================================================================================================

  public static void printTimings ()
  {
    System.out.printf( "Type    Local Target  RMI Target  " + "%n" ) ;
    System.out.printf( "------  ------------  ------------" + "%n" ) ;

    for ( int i = M1 ; i <= M4 ; i++ )
    {
      System.out.printf( "Type %d   %10.2f    %10.2f%n" , i + 1 , timings[LOCAL][i] , timings[RMI][i] ) ;
    }
  }

  //=================================================================================================================================================

  public static void main ( String [] args ) throws Exception
  {
    int    numberOfRepetitions = 100000      ;
    String host                = "localhost" ;
    String name                = "RMITarget" ;

    if ( args.length > 0 )  { numberOfRepetitions = Integer.parseInt( args[0].trim() ) ; }
    if ( args.length > 1 )  { host                =                   args[1].trim()   ; }
    if ( args.length > 2 )  { name                =                   args[2].trim()   ; }

    System.out.println( "Host                  : " + host                ) ;
    System.out.println( "Name                  : " + name                ) ;
    System.out.println( "Number of Repetitions : " + numberOfRepetitions ) ;  System.out.println() ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    // ...

    long   startTime        ;
    long   finishTime       ;
    Registry     registry = LocateRegistry.getRegistry( host )     ;
    RMITargetInterface stub     = (RMITargetInterface) registry.lookup( name ) ;
    LocalTargetInterface local = (LocalTargetInterface) new LocalTargetImplementation();
    startTime =System.nanoTime();
    benchmark(local,stub,numberOfRepetitions);
    
    printTimings();
    finishTime = System.nanoTime() ; 
    double totalTime = finishTime - startTime ; 
    
      System.out.format("Benchmarking took %f seconds.",totalTime/(1000000000)) ; 
    //-----------------------------------------------------------------------------------------------------------------------------------------------
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

