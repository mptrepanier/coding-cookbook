## Designing Instagram

### 1. Requirements Gathering

#### Functional Requirements

* Users should be able to upload/download/view photos.
* Users can search based on photo/video titles.
* Users can follow other users.
* The system should be able to generate and display a user's News Feed consisting of top photos from all the people the user follows.

#### Non-Functional Requirements

* Highly available
* Acceptable latency of 200 ms for news feed generation.
* Consistency can take a hit (in the interest of availability), if a user doesn't see a photo for a while, it should be  fine.
* The system should be highly reliable; any uploaded photo or video should never be lost.

### 2. Some Design Considerations

* System is expected to be read-heavy, as opposed to write.
* Users can upload as many photos as they like. Storage needs to be efficiently managed.
* Low latency is expected when viewing photos.
* Data should be 100% reliable. If a user uploads a photo, the system will guarantee that it will never be lost.

### 3. Capacity Estimation and Constraints

* Assume 500M total users with 1M daily active users.
* 2M new photos a day, 23 photos every second.
* Average photo file size -> 200 kb.
* Total space required for 1 day of photos
  * 2M * 200kB = 400 GB
* Total space required for 10 years:
  * 400 GB * 365 * 10 = 1.425 TB

### 4. High Level System Design

At a high level, we need to support two scenarios - one to upload photos and the other to view/search photos. Our service would need some object storage servers to store photos and some database servers to store metadata info about the photos.

### 5. Data Model/Database Schema

Let's use a NoSQL solution like ElasticSearch for metadata storage and a blob store for 

```
case class Photo(
	photoID: String,
	userID: String,
	photoPath: String,
	photoLat: Int,
	photoLong: Int
)

case class User(
	id: String,
	name: String,
	email: String,
	photos: Seq[String] // Photo IDs of User.
	follows: Seq[String] // User IDs of follows.
	
)
```

### 6. Data Size Estimation

* 500M Users

  ```
  case class User(
  	id: String, // 20 chars = 20 bytes
  	name: String, // 20 chars = 20 bytes
  	email: String, // 32 chars = 32 bytes
  	photos: Seq[String] // Photo IDs of User. // 100 photos = 20 * 100 bytes
  	follows: Seq[String] // User IDs of follows. // 500 follows = 20 * 500
  )
  72 + 2000 + 10000 = 12072 Bytes ~~ 12kB
  500M * 12kB = 6billion kB = 6000 GB = 6 TB
  ```

  ... more napkin math.

  ### 7. Component Design

  Photo uploads (or writes) can be slow as they have to go to disk, reads will be faster (especially if they are served from cache).

  Uploading users can consume all the available connections, as uploading is slow. This means that reads cannot be served. As web servers have a connection limit, we can split servers into reads and writes.

  Separating photo's read and write requests will also allow us to scale and optimize for each operation.

SEE ONENOTE

### 8. Reliability and Redundancy

Load balancers, multiple servers, etc.

Server replication.

Straightforward. ES provides sharding/replication natively, as do the major blob stores.

### 9. Data Sharding

##### Partitioning on User ID

a. Partitioning on UserID. Let's assume we shard on UserID so that we can keep all photos of a user on the same shard. If one DB shard, we will need four shards to store 3.7 TB of data. Let's assume for better performance and scalability, we keep 10 shards.

So, we'll find the shard number by a hash function on the user ID and then store the data there.

To unique identify photos, think `monotonically_increasing_id()` in Spark.

b. What are some issues?

* Heavy users.
* Some users have tons of photos compared to others - non uniform distribution.
* What if we cannot store all pircture of a user on one shard? Distributing could cause multiple reads across shards.
* Storing photos on one shard can cause issues like unavailability if the shard goes down or is busy.

##### Partitioning on Photo ID

Generate unique photo ID then find a shard number through hash. Solves above issues. Photo ID does not need shard ID appended.

How can we generate photo IDs?

Maintain ID table somewhere, add photo ID to table.

Wouldn't this key generating DB be a single point of failure?

Yes, but we can use hashes n shiz and multiple servers.

### 10. Ranking and News Feed Generation

To create the news feed for any given user, we need to fetch the latest, most popular and relevant photos of the people the user follows.

Let's assume we need to fetch top 100 photos for a user's news feed. Our app server will first get a list of people the user follows then fetch metadata info of latest 100 photos for each user. In the final step, the server will submit all these photos to our ranking algorithm which will determine the top 100 photos (based on recency, likeness, etc.) and return them to a user (like ES weights). To improve efficiency, we can pre-generate news feeds.

##### Pre generating the news feed

We can have dedicated servers that are continuously generating user's News Feeds and storing them in a UserNewsFeed table/cache. Whenever any user needs the latest photos in their feed, we can query this table and return the results to the user.

##### What are the different approaches for sending news feed contents to the users?

1. Pull: Clients pull the news feed contents from server on a regular basis or manually whenever they need it. Possible problems a) new data might not be shown to users until pull 2) Most of the time, pulls will result in an empty response if there is now data.
2. Push: Servers push the new data to the users as soon as its available. To efficiently manage this, users have to maintain a long poll request with the server for receiving the updates.
3. Hybrid: Move all users who have a high number of follows to a pull-based model and only push data to those users who have a few hundred or thousand follows.

### 12. News Feed Creation with Sharded Data

One of the most important requirement for the News Feed of any given user is to fetch the latest photos from all the people the user follows. For this, we need to have a mechanism to sort photos on their time of creation.

We can use epoch time for this. We can make photo creation time part of the photo ID for easy filtering.

### 13. Cache Loading and Balancing

Our service would need massive-scale photo delivery system to serve globally distributed users.

We can introduce a cache for metadata servers to cache hot database rows. We can use Memcache to cache the data an app servers before hitting database can quickly check if cache has desired rows.

More intelligent cache?

If 20% of the photos get 80% of views, cache that 20%'s metadata.





