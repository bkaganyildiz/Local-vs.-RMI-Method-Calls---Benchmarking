//***************************************************************************************************************************************************

import java.rmi.Remote          ;
import java.rmi.RemoteException ;

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public interface RMITargetInterface extends Remote
{
  //=================================================================================================================================================

  public void m1 (       ) throws RemoteException ;  // Void     , no   arguments
  public void m2 ( int i ) throws RemoteException ;  // Void     , with arguments
  public int  m3 (       ) throws RemoteException ;  // Non-void , no   arguments
  public int  m4 ( int i ) throws RemoteException ;  // Non-void , with arguments

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

