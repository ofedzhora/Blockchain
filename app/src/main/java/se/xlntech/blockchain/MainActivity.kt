package se.xlntech.blockchain

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().reference
    val blockUtils: BlockUtils = BlockUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = ListAdapter()
        list.layoutManager = LinearLayoutManager(this)

//        database.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError?) {}
//
//            override fun onDataChange(snapshot: DataSnapshot?) {
//                for (blockSnapshot: DataSnapshot in snapshot?.children!!) {
//                    blockchain.add(blockSnapshot.getValue(Block::class.java)!!)
//                }
//                (list.adapter as ListAdapter).updateListItems(blockchain)
//            }
//
//        })

        database.child(BlockUtils.BLOCKCHAIN_BRANCH).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                blockUtils.addBlock(snapshot.getValue(Block::class.java)!!)
                (list.adapter as ListAdapter).updateListItems(blockUtils.getBlockChain())
            }

            override fun onChildRemoved(p0: DataSnapshot?) {}

        })

        mineButton.setOnClickListener {
            startMiningNextBlock()
        }

//        val utils = BlockUtils()
//        val firstBlock = utils.getFirstBlock()
//        val secondBlock = utils.mineBlock("test")
//        blockchain.add(firstBlock)
//        blockchain.add(secondBlock)
//        Log.d("blocks", firstBlock.toString())
//        Log.d("blocks", secondBlock.toString())
//
//        database.setValue(blockchain)

//        val firstBlock = blockUtils.getFirstBlock()
//        database.child(firstBlock.index.toString()).setValue(firstBlock)
    }

    fun startMiningNextBlock() {
//        val minedBlock = blockUtils.getFirstBlock()
        val minedBlock = blockUtils.mineBlock("new block from ${Build.MANUFACTURER} ${System.currentTimeMillis()}")
        if (blockUtils.isValidNewBlock(minedBlock))
            database.child(BlockUtils.NEW_BLOCK_BRANCH).setValue(minedBlock)
    }
}
