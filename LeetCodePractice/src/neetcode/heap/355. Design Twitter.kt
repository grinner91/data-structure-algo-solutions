package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Solution 1: Naive gather + sort on every feed request
 *
 * Idea:
 * - Store every tweet globally with a timestamp.
 * - Store follow relationships.
 * - For getNewsFeed, gather tweets from self + followees, sort by time descending,
 *   and return the latest 10 tweet IDs.
 *
 * Time:
 * - postTweet: O(1)
 * - follow/unfollow: O(1)
 * - getNewsFeed: O(T log T), where T is number of candidate tweets
 *    Worst case: O(T)
 *    Total tweets = 1,000,000
 *    Only 10 belong to your followees
 *    might scan:  ~1,000,000 tweets → O(T)
 * Space:
 * - O(totalTweets + totalFollowEdges)
 */
class TwitterNaive {
    private data class Tweet(val userId: Int, val tweetId: Int, val time: Int)

    private var time = 0
    private val following = HashMap<Int, MutableSet<Int>>()
    private val tweets = mutableListOf<Tweet>()

    fun postTweet(userId: Int, tweetId: Int) {
        tweets.add(Tweet(userId, tweetId, time++))
    }

    fun getNewsFeed(userId: Int): List<Int> {
        val users = HashSet<Int>()
        users.add(userId)
        users.addAll(following[userId].orEmpty())

        return tweets
            .asReversed()
            .asSequence()
            .filter { it.userId in users }
            .take(10)
            .map { it.tweetId }
            .toList()
    }

    fun follow(followerId: Int, followeeId: Int) {
        if (followerId == followeeId) return
        following.getOrPut(followerId) { HashSet() }.add(followeeId)
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        following[followerId]?.remove(followeeId)
    }
}

class TwitterMaxHeap {
    private data class Tweet(val id: Int, val time: Int)
    private data class FeedEntry(val userId: Int, val tweetIndex: Int)

    private var time = 0
    private val following = HashMap<Int, MutableSet<Int>>()
    private val tweets = HashMap<Int, MutableList<Tweet>>()

    fun postTweet(userId: Int, tweetId: Int) {
        tweets.getOrPut(userId) { mutableListOf() }.add(Tweet(tweetId, time))
        time++
    }

    fun getNewsFeed(userId: Int): List<Int> {
        // Step 1: collect all relevant users (self + followees)
        val users = mutableListOf<Int>()
        users.add(userId)
        users.addAll(following[userId].orEmpty())

        // Step 2: push latest tweet of each user into heap
        val maxHeap = PriorityQueue<FeedEntry> { a, b ->
            val t1 = tweets[a.userId]!![a.tweetIndex]
            val t2 = tweets[b.userId]!![a.tweetIndex]
            t2.time - t1.time
        }

        users.forEach { u ->
            tweets[u]?.let { list ->
                maxHeap.add(FeedEntry(u, list.lastIndex))
            }
        }

        // Step 3: k-way merge
        val result = mutableListOf<Int>()
        while (maxHeap.isNotEmpty() && result.size < 10) {
            val feed = maxHeap.poll()
            val twt = tweets[feed.userId]!![feed.tweetIndex]
            result.add(twt.id)

            val prevIndex = feed.tweetIndex - 1
            if (prevIndex >= 0) {
                maxHeap.offer(FeedEntry(feed.userId, prevIndex))
            }
        }
        return result
    }

    fun follow(followerId: Int, followeeId: Int) {
        if (followerId == followeeId) return
        following.getOrPut(followerId) { mutableSetOf() }.add(followeeId)
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        following[followerId]?.remove(followeeId)
    }
}

//optimal
/**
 * Solution 3: Optimal linked-list tweets per user + max heap merge
 *
 * Idea:
 * - Each user stores tweets as a singly linked list, newest first.
 * - For getNewsFeed, push the head tweet of self + followees into a max heap.
 * - Pop newest, append tweetId to result, then push the next node from that same list.
 *
 * This is the classic interview-optimal design.
 *
 * Time:
 * - postTweet: O(1)
 * - follow/unfollow: O(1)
 * - getNewsFeed: O((F + 10) log F), where F is number of followed users + self
 *
 * Initialization:   O(F log F)
 * Extraction:       O(10 log F)
 * --------------------------------
 * Total:            O((F + 10) log F)
 *
 * Space:
 * - O(totalTweets + totalFollowEdges)
 */
class TwitterLinkedList {
    data class TweetNode(val id: Int, val time: Int, val next: TweetNode? = null)

    private var time = 0
    private val tweets = HashMap<Int, TweetNode>()
    private val following = HashMap<Int, MutableSet<Int>>()

    fun postTweet(userId: Int, tweetId: Int) {
        tweets[userId] = TweetNode(tweetId, time, tweets[userId])
        time++
    }

    fun getNewsFeed(userId: Int): List<Int> {
        //list all users
        val users = HashSet<Int>()
        users.add(userId)
        users.addAll(following[userId].orEmpty())

        //prepare max Heap with latest posts
        val maxHeap = PriorityQueue<TweetNode>() { a, b -> b.time - a.time }
        users.forEach { u ->
            tweets[u]?.let { maxHeap.offer(it) }
        }
        //k-way merge
        val result = mutableListOf<Int>()
        while (maxHeap.isNotEmpty() && result.size < 10) {
            val twt = maxHeap.poll()
            result.add(twt.id)
            twt.next?.let { maxHeap.offer(it) }
        }
        return result
    }

    fun follow(followerId: Int, followeeId: Int) {
        if (followerId == followeeId) return
        following.getOrPut(followerId) { mutableSetOf() }.add(followeeId)
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        following[followerId]?.remove(followeeId)
    }
}

class DesignTwitterTest {

    private data class TwitterApi(
        val postTweet: (Int, Int) -> Unit,
        val getNewsFeed: (Int) -> List<Int>,
        val follow: (Int, Int) -> Unit,
        val unfollow: (Int, Int) -> Unit,
    )

    private val factories = listOf(
//        { TwitterNaive().toApi() },
//        { TwitterMaxHeap().toApi() },
        { TwitterLinkedList().toApi() },
    )

    @Test
    fun exampleCase() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 5)
            assertEquals(listOf(5), twitter.getNewsFeed(1))

            twitter.follow(1, 2)
            twitter.postTweet(2, 6)
            assertEquals(listOf(6, 5), twitter.getNewsFeed(1))

            twitter.unfollow(1, 2)
            assertEquals(listOf(5), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun userSeesOwnTweetsInReverseChronologicalOrder() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 10)
            twitter.postTweet(1, 11)
            twitter.postTweet(1, 12)

            assertEquals(listOf(12, 11, 10), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun feedContainsOnlyLatestTenTweets() {
        factories.forEach { create ->
            val twitter = create()

            for (tweetId in 1..12) {
                twitter.postTweet(1, tweetId)
            }

            assertEquals(
                listOf(12, 11, 10, 9, 8, 7, 6, 5, 4, 3),
                twitter.getNewsFeed(1)
            )
        }
    }

    @Test
    fun followAddsTweetsFromFollowee() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 100)
            twitter.postTweet(2, 200)
            twitter.follow(1, 2)

            assertEquals(listOf(200, 100), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun unfollowRemovesTweetsFromFollowee() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 100)
            twitter.postTweet(2, 200)
            twitter.follow(1, 2)
            twitter.unfollow(1, 2)

            assertEquals(listOf(100), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun cannotFollowSelf() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 1)
            twitter.follow(1, 1)
            twitter.postTweet(1, 2)

            assertEquals(listOf(2, 1), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun unfollowNonExistingRelationDoesNothing() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 7)
            twitter.unfollow(1, 2)

            assertEquals(listOf(7), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun multipleFolloweesAreMergedByRecency() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(1, 101)
            twitter.postTweet(2, 201)
            twitter.postTweet(3, 301)
            twitter.follow(1, 2)
            twitter.follow(1, 3)

            assertEquals(listOf(301, 201, 101), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun followAfterOldTweetsStillShowsThoseTweets() {
        factories.forEach { create ->
            val twitter = create()

            twitter.postTweet(2, 20)
            twitter.postTweet(2, 21)
            twitter.follow(1, 2)

            assertEquals(listOf(21, 20), twitter.getNewsFeed(1))
        }
    }

    @Test
    fun emptyFeedForUserWithNoTweetsAndNoFollowees() {
        factories.forEach { create ->
            val twitter = create()
            assertEquals(emptyList<Int>(), twitter.getNewsFeed(42))
        }
    }

    private fun TwitterNaive.toApi() = TwitterApi(
        postTweet = ::postTweet,
        getNewsFeed = ::getNewsFeed,
        follow = ::follow,
        unfollow = ::unfollow,
    )

    //
    private fun TwitterMaxHeap.toApi() = TwitterApi(
        postTweet = ::postTweet,
        getNewsFeed = ::getNewsFeed,
        follow = ::follow,
        unfollow = ::unfollow,
    )

    private fun TwitterLinkedList.toApi() = TwitterApi(
        postTweet = ::postTweet,
        getNewsFeed = ::getNewsFeed,
        follow = ::follow,
        unfollow = ::unfollow,
    )
}