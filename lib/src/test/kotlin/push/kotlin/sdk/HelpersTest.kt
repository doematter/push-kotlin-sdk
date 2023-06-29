package push.kotlin.sdk

import kotlin.test.Test
import kotlin.test.assertEquals

class HelpersTest {
    @Test fun messageDecryptionTest() {
        val messageContent = "U2FsdGVkX18+UrnRfChnSk36MaqUQC7gD7r8aD2PKtI="
        val encryptedSecret = ENC_MESSAGE

        val decryptedMessage = Helpers.decryptMessage(encryptedSecret, messageContent, PGP_PK)
        assertEquals(decryptedMessage, "welcome to push")
    }
}