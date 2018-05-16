package se.xlntech.blockchain

import java.security.MessageDigest


class ShaUtils {

    companion object {
        fun getSHA256(str: String): String {
            val messageDigest: MessageDigest
            var encodeStr = ""
            try {
                messageDigest = MessageDigest.getInstance("SHA-256")
                messageDigest.update(str.toByteArray(charset("UTF-8")))
                encodeStr = byte2Hex(messageDigest.digest())
            } catch (e: Exception) {
                println("getSHA256 is error" + e.message)
            }

            return encodeStr
        }

        private fun byte2Hex(bytes: ByteArray): String {
            val builder = StringBuilder()
            var temp: String
            for (i in bytes.indices) {
                temp = Integer.toHexString(bytes[i].toInt() and 0xFF)
                if (temp.length == 1) {
                    builder.append("0")
                }
                builder.append(temp)
            }
            return builder.toString()
        }
    }
}