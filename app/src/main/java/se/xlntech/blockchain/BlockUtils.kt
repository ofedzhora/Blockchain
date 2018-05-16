package se.xlntech.blockchain

import android.util.Log

class BlockUtils {

    companion object {
        const val BLOCKCHAIN_BRANCH = "blockchain"
        const val NEW_BLOCK_BRANCH = "new_block"
    }

    private var blockChain: ArrayList<Block> = ArrayList()
    private val difficulty: Int = 4
    private val difficultyTarget = "".padStart(difficulty, '0')

    private fun calculateHash(index: Int, previousHash: String?, timestamp: Long, data: String?, nonce: Int): String {
        val builder = StringBuilder(index)
        builder.append(previousHash).append(timestamp).append(data).append(nonce)
        return ShaUtils.getSHA256(builder.toString())
    }

    fun getLatestBlock(): Block {
        return blockChain[blockChain.size - 1]
    }

    fun getFirstBlock(): Block {
        val nextIndex = 0
        var nextTimestamp = System.currentTimeMillis()
        var nextHash = "11111111111111111111"
        var nonce = -1
        val previousBlockHash = "0"
        val blockData = "Hello block"
        while (nextHash.substring(0, difficulty) != difficultyTarget) {
            nonce++
            nextHash = calculateHash(nextIndex, previousBlockHash, nextTimestamp, blockData, nonce)
            if (nonce == Int.MAX_VALUE) {
                nextTimestamp = System.currentTimeMillis()
                nonce = -1
            }
            Log.d("First block info","timestamp $nextTimestamp nonce $nonce")
        }

        return Block(nextIndex, previousBlockHash, nextTimestamp, blockData, nextHash, nonce)
    }

    fun mineBlock(blockData: String): Block {
        if (BuildConfig.DEBUG && blockChain.size == 0)
            return getFirstBlock()

        val previousBlock = this.getLatestBlock()
        val nextIndex = previousBlock.index + 1
        var nextTimestamp = System.currentTimeMillis()
        var nextHash = "11111111111111111111"
        var nonce = -1
        while (nextHash.substring(0, difficulty) != difficultyTarget) {
            nonce++
            nextHash = calculateHash(nextIndex, previousBlock.hash, nextTimestamp, blockData, nonce)
            if (nonce == Int.MAX_VALUE) {
                nextTimestamp = System.currentTimeMillis()
                nonce = -1
            }
            Log.d("block info","timestamp $nextTimestamp nonce $nonce")

        }
        return Block(nextIndex, previousBlock.hash, nextTimestamp, blockData, nextHash, nonce)
    }

    fun addBlock(newBlock: Block) {
        if (newBlock.index == 0 && newBlock.previousHash == "0" && blockChain.size == 0) {
            blockChain.add(newBlock)
            return
        }
        if (isValidNewBlock(newBlock)) {
            blockChain.add(newBlock)
        }
    }

    private fun isFirstBlock(block: Block): Boolean {
        return block.index == 0 && block.previousHash == "0" && blockChain.size == 0
    }

    fun isValidNewBlock(newBlock: Block): Boolean {
        if (isFirstBlock(newBlock))
            return true

        val previousBlock = getLatestBlock()
        if (previousBlock.index + 1 != newBlock.index) {
            Log.d("Block error","invalid index")
            return false
        } else if (previousBlock.hash != newBlock.previousHash) {
            Log.d("Block error","invalid previousHash")
            return false
        } else {
            val hash = calculateHash(newBlock.index, newBlock.previousHash, newBlock.timestamp,
                    newBlock.data, newBlock.nonce)
            if (hash != newBlock.hash) {
                Log.d("Block error","invalid hash: " + hash + " " + newBlock.hash)
                return false
            }
        }
        if(newBlock.hash.substring( 0, difficulty) != difficultyTarget) {
            Log.d("Block error","This block hasn't been mined")
            return false
        }
        return true
    }

    fun getBlockChain(): ArrayList<Block> {
        return blockChain
    }
}