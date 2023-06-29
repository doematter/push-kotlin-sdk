package push.kotlin.sdk

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class PushChat{
  data class Message(
    val fromCAIP10: String,
    val toCAIP10: String,
    val fromDID: String,
    val toDID: String,
    var messageContent: String,
    val messageType: String,
    val signature: String,
    val timestamp: Long,
    val sigType: String,
    val encType: String,
    val encryptedSecret: String,
    val link: String
  )

  data class Feed(
    var msg: Message,
    var did: String?,
    var wallets: String?,
    var profilePicture: String?,
    var publicKey: String?,
    var about: String?,
    var name: String?,
    var threadhash: String,
    var intent: String?,
    var intentSentBy: String?,
    var intentTimestamp: String?,
    var combinedDID: String,
    var cid: String?,
    var chatId: String?,
    var deprecated: Boolean?,
    var deprecatedCode: String?
  )

  data class Feeds(
    var chats: Array<Feed>
  )

  data class GetChatsOptions(
    val account:String,
    val pgpPrivateKey:String,
    val toDecrypt:Boolean,
    val page:Number,
    val limit:Number,
    val env:ENV
  )

  companion object{
    public  fun getChats(options:PushChat.GetChatsOptions):Array<Feed>?{
      val userAddress = Helpers.walletToCAIP(options.account)

      val url = PushURI.getChats(options.env,userAddress,options.page,options.limit)

      // Create an OkHttpClient instance
      val client = OkHttpClient()

      // Create a request object
      val request = Request.Builder()
              .url(url)
              .build()

      val response = client.newCall(request).execute()

      if (response.isSuccessful) {
        val jsonResponse = response.body?.string()
        val gson = Gson()
        var feeds = gson.fromJson(jsonResponse, PushChat.Feeds::class.java).chats

        feeds = getFeedsMsg(feeds, options.pgpPrivateKey,options.toDecrypt,options.env)

        return feeds
      } else {
        println("Error: ${response.code} ${response.message}")
      }

      // Close the response body
      response.close()
      return null
    }

    private fun getFeedsMsg(feeds:Array<Feed>, pgpPrivateKey: String, toDecrypt: Boolean, env: ENV):Array<Feed>{
      var newFeed =  arrayOf<Feed>()
      feeds.forEach { feed ->
        val message = PushChat.resolveIpfs(feed.threadhash, env) ?: throw  IllegalStateException("")

        if (toDecrypt && message.encType == "pgp"){
          val messageDecrypted = Helpers.decryptMessage(message.encryptedSecret, message.messageContent, pgpPrivateKey)
          message.messageContent = messageDecrypted
        }

        feed.msg = message

        newFeed += feed
      }

      return newFeed
    }

    public fun resolveIpfs(cid:String, env:ENV):PushChat.Message? {
      val url =  PushURI.getCID(env, cid)

      // Create an OkHttpClient instance
      val client = OkHttpClient()

      // Create a request object
      val request = Request.Builder()
              .url(url)
              .build()

      val response = client.newCall(request).execute()

      if (response.isSuccessful) {
        val jsonResponse = response.body?.string()
        val gson = Gson()
        val apiResponse = gson.fromJson(jsonResponse, PushChat.Message::class.java)
        return apiResponse
      } else {
        println("Error: ${response.code} ${response.message}")
      }

      // Close the response body
      response.close()

      return null
    }
  }
}