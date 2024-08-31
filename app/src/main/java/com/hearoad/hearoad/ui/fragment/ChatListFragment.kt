package com.hearoad.hearoad.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hearoad.hearoad.R
import com.hearoad.hearoad.data.network.ChatRoom
import com.hearoad.hearoad.data.network.RetrofitChatting
import com.hearoad.hearoad.ui.adapter.ChatListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatListAdapter: ChatListAdapter
    private val chatRooms = mutableListOf<ChatRoom>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        chatListAdapter = ChatListAdapter(chatRooms)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = chatListAdapter

        fetchChatRooms() // 데이터 불러오기
        return view
    }

    private fun fetchChatRooms() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitChatting().apiService.getChatRooms()
                withContext(Dispatchers.Main) {
                    chatRooms.clear()
                    chatRooms.addAll(response)
                    chatListAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
