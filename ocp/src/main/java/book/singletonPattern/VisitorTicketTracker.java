package book.singletonPattern;

// Lazy instantiation
public class VisitorTicketTracker {
    private static VisitorTicketTracker instance;
    private VisitorTicketTracker() {}

    public static VisitorTicketTracker getInstance() { // you can type synchronized and will be thread safe
        if(instance == null) {
            instance = new VisitorTicketTracker();  //NOT THREAD SAFE
        }
        return instance;
    }

    // Data acces methods...
}

/*
    The synchronized implementation of getInstance(), while correctly preventing multiple
    singleton objects from being created, has the problem that every single call to this
    method will require synchronization. In practice, this can be costly and can impact
    performance. Synchronization is only needed the first time that the object is created.
    The solution is to use double‐checked locking, a design pattern in which we first test if
    synchronization is needed before actually acquiring any locks. The following is an example rewrite of this method using double‐checked locking:
        private static volatile VisitorTicketTracker instance;
        public static VisitorTicketTracker getInstance() {
            if(instance == null) {
                synchronized(VisitorTicketTracker.class) {
                    if(instance == null) {
                        instance = new VisitorTicketTracker();
                    }
                }
            }
            return instance;
        }
 */
