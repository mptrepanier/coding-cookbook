## Scalability

### Horizontal Scaling

##### Advantages

* Can scale using cheaper hardware (than going vertically).
* Can provide specialized dedicated servers which are optimized for different data types.
* Enables replication of servers for redundancy.

##### Complexities Introduced

* Requires a load balancer between the client and the web servers.
  * Load balancer - public IP.
  * Server - private IP.
  * Can balance via round robin, etc.
* Different clients have different usage profiles.
  * Multiple heavy users can end up on one server, might end up with loss of service.
  * Caching can contribute to heavy loads (the DNS lookup won't occur if cached).
* Requires appropriate session handling. (Sessions need to be sticky.)
  * User logs in on server one,  by random chance is directed to server two later. Will be required to log in again.
  * Metadata needs to be shared (what is in my cart, for example) or user needs to be routed to same server.
    * Session file server?
    * RAID?

##### RAID - Redundant Array of Identical Disks

* RAID 0
  * Data is striped across disks.
  * No replication.
  * Parallel read.
* RAID 1
  * Data is replicated.
  * Two drives.
* RAID 5
  * Only one redundant drive.
* RAID 10
  * Replication and parallel reads.

##### Load Balancers

* Software:
  * Amazon: Elastic load balancer.
  * HAProxy

##### Caching

* MySQL query cache.

  * Caches repeated queries.

* MemCache

  * A memory cache.

  * Stores whatever you want in RAM.

    ```
    val user = memcacheGet(username) match {
    	case Some(un) => un
    	case _ => {
    		val un = queryForUser(username)
    		memcacheSet(un)
    		un
    		}
    }
    ```

##### Data Replication

* All about making automatic copies of something.
* Generally have master and slave databases.
  * In theory, master and slave are identical.
* Excellent for read heavy websites.
  * Write multiple times ones.
  * Reads (SELECTS) are routed to slaves.
  * Writes are directed to master. (Think ES.)
* Single point of failure for writes.
  * Multiple masters avoid this.



##### 