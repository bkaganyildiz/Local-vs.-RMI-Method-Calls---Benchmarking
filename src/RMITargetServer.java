
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

//***************************************************************************************************************************************************

// import(s)

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public class RMITargetServer
{
  //=================================================================================================================================================

  public static void main ( String args[] ) throws Exception
  {
    String name = "RMITarget" ;  // The name to which the RMI object will be bound in RMI Registry

    if ( args.length > 0 )  { name = args[0].trim() ; }

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    // ...
    RMITargetImplementation obj  = new RMITargetImplementation()                                    ;
    RMITargetInterface      stub = (RMITargetInterface) UnicastRemoteObject.exportObject( obj , 0 ) ;

    LocateRegistry.getRegistry().rebind( name , stub ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    System.out.println( "RMI Server ready" ) ;  System.out.println() ;
    System.out.println( "Enter Q to quit"  ) ;  System.out.println() ;

    Scanner input = new Scanner( System.in ) ;

    while ( true )  { String command = input.nextLine().trim() ;  if ( command.equals( "Q" ) || command.equals( "q" ) )  { break ; } }

    System.exit( 0 ) ;
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

