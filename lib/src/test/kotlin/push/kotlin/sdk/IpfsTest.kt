package push.kotlin.sdk

import kotlin.test.assertEquals
import kotlin.test.Test

class IpfsTest {
    @Test fun resolvesIPFSHash() {
        val cid = "bafyreidhqkzwewbmuof7onnm3jzr3frgyzyxb7afyjjrprf2kcpfjb4hqq"

        val message = PushChat.resolveIpfs(cid, ENV.staging) ?: throw IllegalStateException("");

        assertEquals(message.fromDID, "eip155:0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c")
        assertEquals(message.toDID, "eip155:0x03fAD591aEb926bFD95FE1E38D51811167a5ad5c")
    }

    @Test fun chatFeedTest() {
        val feedOptions = PushChat.GetChatsOptions(
    "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c",
            PGP_PK, false,1, 10, ENV.staging
        ) ?: throw  IllegalStateException("")

        val feeds = PushChat.getChats(feedOptions) ?: throw  IllegalStateException("")
        assertEquals(feeds.count(), 10)
    }

    @Test fun chatFeedTestDecrypt() {
        val feedOptions = PushChat.GetChatsOptions(
                "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c",
                PGP_PK, true,1, 3, ENV.staging
        ) ?: throw  IllegalStateException("")

        val feeds = PushChat.getChats(feedOptions) ?: throw  IllegalStateException("")
        feeds.forEach {feed -> assert(feed.msg.messageContent.isNotEmpty()) }
    }

    @Test fun chatMessageDecryptionTest() {
        val cid = "bafyreidhqkzwewbmuof7onnm3jzr3frgyzyxb7afyjjrprf2kcpfjb4hqq"
        val message = PushChat.resolveIpfs(cid, ENV.staging) ?: throw IllegalStateException("");

        println(message.messageContent)
    }
}