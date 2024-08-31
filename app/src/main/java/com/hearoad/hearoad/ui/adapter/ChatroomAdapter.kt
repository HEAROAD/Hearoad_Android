package com.hearoad.hearoad.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hearoad.hearoad.data.model.response.ChatMessage
import com.hearoad.hearoad.databinding.ItemMessageBinding
import com.hearoad.hearoad.databinding.ItemUsermessageBinding

class ChatroomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<ChatMessage>()

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].type) {
            "USER" -> VIEW_TYPE_USER
            "PARTNER" -> VIEW_TYPE_PARTNER
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = ItemUsermessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                UserMessageViewHolder(binding)
            }
            VIEW_TYPE_PARTNER -> {
                val binding = ItemMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                PartnerMessageViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserMessageViewHolder -> holder.bind(messages[position])
            is PartnerMessageViewHolder -> holder.bind(messages[position])
        }
    }

    override fun getItemCount(): Int = messages.size

    fun submitList(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    class UserMessageViewHolder(private val binding: ItemUsermessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.tvUsermessageMessage.text = message.message

            if (!message.ksl.isNullOrEmpty()) {
                binding.ivUsermessageHandsign.visibility = View.VISIBLE
                binding.ivUsermessageHandsign.setVideoURI(Uri.parse(message.ksl))

                // 비디오 클릭 시 재생
                binding.ivUsermessageHandsign.setOnClickListener {
                    if (!binding.ivUsermessageHandsign.isPlaying) {
                        binding.ivUsermessageHandsign.start()
                    }
                }

                // 비디오가 끝나면 다시 처음으로 돌아가도록 설정
                binding.ivUsermessageHandsign.setOnCompletionListener { mp ->
                    mp.seekTo(0)
                }
            } else {
                binding.ivUsermessageHandsign.visibility = View.GONE
            }
        }

    }

    class PartnerMessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.tvMessageMessage.text = message.message

            if (!message.ksl.isNullOrEmpty()) {
                binding.ivMessageHandsign.visibility = View.VISIBLE
                binding.ivMessageHandsign.setVideoURI(Uri.parse(message.ksl))

                // 비디오 클릭 시 재생
                binding.ivMessageHandsign.setOnClickListener {
                    if (!binding.ivMessageHandsign.isPlaying) {
                        binding.ivMessageHandsign.start()
                    }
                }

                // 비디오가 끝나면 다시 처음으로 돌아가도록 설정
                binding.ivMessageHandsign.setOnCompletionListener { mp ->
                    mp.seekTo(0)
                }
            } else {
                binding.ivMessageHandsign.visibility = View.GONE
            }
        }
    }


    companion object {
        const val VIEW_TYPE_USER = 1
        const val VIEW_TYPE_PARTNER = 2
    }
}