package push.kotlin.sdk

import kotlin.test.Test
import kotlin.test.assertEquals

class AesTest {
  @Test fun AESCBCDecryptionWorks() {
    val aes_key = "H58gh7d1AhOqQoU"
    val cipherMessage = "U2FsdGVkX18+UrnRfChnSk36MaqUQC7gD7r8aD2PKtI="
    val actualMessage = "welcome to push"
    val msg = AESCBC.decrypt(aes_key, cipherMessage)
    assertEquals(msg, actualMessage)
  }

}
