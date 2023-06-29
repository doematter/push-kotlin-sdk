package push.kotlin.sdk

import org.bouncycastle.openpgp.PGPPublicKeyRing
import org.bouncycastle.openpgp.PGPSecretKeyRing
import org.bouncycastle.util.io.Streams
import org.pgpainless.PGPainless
import org.pgpainless.decryption_verification.ConsumerOptions
import org.pgpainless.encryption_signing.EncryptionOptions
import org.pgpainless.encryption_signing.ProducerOptions
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Pgp {

  companion object {
//
//        val userPublicKey:PGPPublicKeyRing = PGPainless.readKeyRing()
//                .publicKeyRing(UserPublicKey) ?: throw IllegalStateException("Secret key not found");
//
//

    public fun encrypt(message: String, userPublicKey: PGPPublicKeyRing): String {
      val outputStream = ByteArrayOutputStream()
      val encryptionStream = PGPainless.encryptAndOrSign()
              .onOutputStream(outputStream)
              .withOptions(
                      ProducerOptions.encrypt(
                              EncryptionOptions().addRecipient(userPublicKey)
                      ).setAsciiArmor(true)
              )

      val inputStream = ByteArrayInputStream(message.toByteArray())
      Streams.pipeAll(inputStream, encryptionStream);
      encryptionStream.close();

      return outputStream.toByteArray().toString(Charsets.UTF_8)
    }

    public fun decrypt(encryptedMessage: String, pgpPrivateKey: String): String {
      val secretKey:PGPSecretKeyRing = PGPainless.readKeyRing()
              .secretKeyRing(pgpPrivateKey) ?: throw IllegalStateException("Secret key not found");

      val decryptedInputStream = ByteArrayInputStream(encryptedMessage.toByteArray())
      val decryptionStream = PGPainless.decryptAndOrVerify()
              .onInputStream(decryptedInputStream)
              .withOptions(
                      ConsumerOptions().addDecryptionKey(secretKey)
              )

      val outputStream = ByteArrayOutputStream()
      Streams.pipeAll(decryptionStream, outputStream);
      decryptionStream.close();

      return outputStream.toString(Charsets.UTF_8)
    }
  }
}