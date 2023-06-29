# restapi
This package gives access to Push Protocol (Push Nodes) APIs. Visit [Developer Docs](https://docs.push.org/developers) or [Push.org](https://push.org) to learn more.

-----
## For Chat

-----

### **Get user data for chat**

```kotlin
  val userAddress = "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c"
  val user:PushUser.UserProfile? = PushUser.getUser(userAddress, ENV.staging)
```

### **Get Inbox Feeds**
```kotlin
val feedOptions = PushChat.GetChatsOptions(
    "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c",
    PGP_PRIVATE_KEY_STRING, true, 1, 10, ENV.staging
)

val feeds = PushChat.getChats(feedOptions)
```

### **Get Chat Requests**
```kotlin
val feedOptions = PushChat.GetChatsOptions(
    "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c",
    PGP_PRIVATE_KEY_STRING, false, 1, 10, ENV.staging
)

val requests = PushChat.getChatRequests(feedOptions)
```

### ***Get Latest Conversation  IPFS Hash***
```kotlin 
// null if no conversation between the users
val hash:String? = PushChat.getConversationHash(
    "0x03fAD591aEb926bFD95FE1E38D51811167a5ad5c",
    "0xD26A7BF7fa0f8F1f3f73B056c9A67565A6aFE63c", 
    ENV.staging
) 
```

### ***Get latest message of the hash***
```kotlin 
// null if no conversation between the users
val message:PushChat.Message = PushChat.getLatestMessage(
    "bafyreidhqkzwewbmuof7onnm3jzr3frgyzyxb7afyjjrprf2kcpfjb4hqq", 
    PGP_PRIVATE_KEY_STRING,
    ENV.staging
)
```

### ***Get Conversation History***
```kotlin
val messages:Array<Message> = PushChat.getConversationHistory(
    "bafyreidhqkzwewbmuof7onnm3jzr3frgyzyxb7afyjjrprf2kcpfjb4hqq", 
    5,
    PGP_PRIVATE_KEY_STRING,
    true, 
    ENV.staging
)
```