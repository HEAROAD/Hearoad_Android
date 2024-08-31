package com.hearoad.hearoad.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearoad.hearoad.R
import com.hearoad.hearoad.data.network.ChatRoom

// ChatListAdapter 클래스
class ChatListAdapter(private val chatRooms: List<ChatRoom>) :
    RecyclerView.Adapter<ChatListAdapter.ChatRoomViewHolder>() {

    // ViewHolder 클래스: 개별 아이템 뷰를 관리
    class ChatRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)

        // 데이터 바인딩 메서드
        fun bind(chatRoom: ChatRoom) {
            nameTextView.text = chatRoom.name
            lastMessageTextView.text = chatRoom.lastMessage
        }
    }

    // onCreateViewHolder: 새로운 뷰를 생성할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_room, parent, false) // item_chat_room 레이아웃을 inflate
        return ChatRoomViewHolder(view)
    }

    // onBindViewHolder: ViewHolder에 데이터를 바인딩할 때 호출
    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(chatRooms[position])
    }

    // getItemCount: 리스트의 전체 아이템 개수 반환
    override fun getItemCount() = chatRooms.size
}
