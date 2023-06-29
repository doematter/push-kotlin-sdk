package push.kotlin.sdk

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

data class ApiResponse(
        val fromCAIP10: String,
        val toCAIP10: String,
        val fromDID: String,
        val toDID: String,
        val messageContent: String,
        val messageType: String,
        val signature: String,
        val timestamp: Long,
        val sigType: String,
        val encType: String,
        val encryptedSecret: String,
        val link: String
)


class PushChat{
  companion object{

    public fun resolveIpfs() {
      val url = "https://backend-staging.epns.io/apis/v1/ipfs/bafyreidhqkzwewbmuof7onnm3jzr3frgyzyxb7afyjjrprf2kcpfjb4hqq"

      // Create an OkHttpClient instance
      val client = OkHttpClient()

      // Create a request object
      val request = Request.Builder()
              .url(url)
              .build()

      // Execute the request
      val response = client.newCall(request).execute()

      // Check if the request was successful
      if (response.isSuccessful) {
        // Parse the response JSON into your data class
        val jsonResponse = response.body?.string()
        val gson = Gson()
        val apiResponse = gson.fromJson(jsonResponse, ApiResponse::class.java)

        // Now you can access the parsed data
        println("From CAIP10: ${apiResponse.fromCAIP10}")
        println("To CAIP10: ${apiResponse.toCAIP10}")
        // ... access other properties as needed
      } else {
        println("Error: ${response.code} ${response.message}")
      }

      // Close the response body
      response.close()
    }
  }
}