package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedos and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }

  public boolean fireLasers(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedos(FiringMode firingMode) {

    boolean firingSuccess = false;

    if(firingMode == FiringMode.SINGLE)
    {
        firingSuccess = tryToFireNextWithDefault();
    }
    else if(firingMode == FiringMode.ALL)
    {
        // try to fire both of the torpedos
	//Change for branch-A
        //Change for branch-B
	if(!primaryTorpedoStore.isEmpty() && !secondaryTorpedoStore.isEmpty())
	{
	    firingSuccess = primaryTorpedoStore.fire(1) && secondaryTorpedoStore.fire(1);
	}
    }

    return firingSuccess;
  }

  /**
  * Tries to fire from the next torpedo store. If it's empty it will default to the other.
  *
  * @return whether a torpedo was fired successfully
  */
  private boolean tryToFireNextWithDefault()
  {
      boolean firingSuccess = false;
      TorpedoStore ts = wasPrimaryFiredLast ? secondaryTorpedoStore : primaryTorpedoStore;

      if(!ts.isEmpty())
      {
          firingSuccess = fireFromTorpedoStore(ts);
      }
      else
      {
          ts = wasPrimaryFiredLast ? primaryTorpedoStore : secondaryTorpedoStore;
          if(!ts.isEmpty())
              firingSuccess = fireFromTorpedoStore(ts);
      }

      return firingSuccess;
  }

  /**
  * Tries to fire a torpedo from the given torpedo store.
  *
  * @param torpedoStore the torpedo store from which we want to fire
  *
  * @return whether a torpedo was fired successfully
  */
  private boolean fireFromTorpedoStore(TorpedoStore torpedoStore)
  {
      boolean firingSuccess = torpedoStore.fire(1);
      wasPrimaryFiredLast = !wasPrimaryFiredLast;

      return firingSuccess;
  }
}