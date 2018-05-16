package se.xlntech.blockchain

class Block (var index: Int, var previousHash: String, var timestamp: Long, var data: String, var hash: String, var nonce: Int) {

    constructor() : this(0, "", 0, "", "", 0)

//    private var index: Int = 0
//    private var previousHash: String? = null
//    private var timestamp: Long = 0
//    private var data: String? = null
//    private var hash: String? = null
//
//    fun Block(index: Int, previousHash: String, timestamp: Long, data: String, hash: String) {
//        this.index = index
//        this.previousHash = previousHash
//        this.timestamp = timestamp
//        this.data = data
//        this.hash = hash
//    }
//
//    fun getIndex(): Int {
//        return index
//    }
//
//    fun setIndex(index: Int) {
//        this.index = index
//    }
//
//    fun getPreviousHash(): String? {
//        return previousHash
//    }
//
//    fun setPreviousHash(previousHash: String) {
//        this.previousHash = previousHash
//    }
//
//    fun getTimestamp(): Long {
//        return timestamp
//    }
//
//    fun setTimestamp(timestamp: Long) {
//        this.timestamp = timestamp
//    }
//
//    fun getData(): String? {
//        return data
//    }
//
//    fun setData(data: String) {
//        this.data = data
//    }
//
//    fun getHash(): String? {
//        return hash
//    }
//
//    fun setHash(hash: String) {
//        this.hash = hash
//    }
}