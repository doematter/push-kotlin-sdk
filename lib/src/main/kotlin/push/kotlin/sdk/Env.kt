package push.kotlin.sdk

enum class ENV {
    prod, staging, dev
}

object PushURI {
    fun getCID(env: ENV, cid: String): String {
        return "${getBaseUri(env)}/ipfs/$cid"
    }

    fun getUser(env:ENV, userAddress:String):String{
        return "${getBaseUri(env,"v2")}/users?caip10=eip155:${userAddress}"
    }

    fun getChats(env:ENV, did:String, page:Number=1, limit:Number=10):String{
        return "${getBaseUri(env)}/chat/users/$did/chats?page=$page&limit=$limit"
    }

    fun getBaseUri(env: ENV, version:String="v1"):String {

        var baseURL = when (env) {
            ENV.prod -> "https://backend-staging.epns.io"
            ENV.staging -> "https://backend-staging.epns.io"
            ENV.dev -> "https://backend-dev.epns.io"
        }

        baseURL += "/apis/$version"
        return baseURL
    }

}
