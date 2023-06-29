package push.kotlin.sdk

class Helpers {
    companion object{
        fun walletToCAIP(address:String):String{
            return  "eip155:${address}"
        }

        fun decryptMessage( encryptedSecret:String, messageContent:String, pgpPrivateKey:String):String{
            val AESKey = Pgp.decrypt(encryptedSecret, pgpPrivateKey)
            val message = AESCBC.decrypt(AESKey, messageContent)
            return message
        }
    }
}