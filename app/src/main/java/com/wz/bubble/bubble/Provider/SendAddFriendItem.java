package com.wz.bubble.bubble.Provider;

import android.content.Context;

import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wz.bubble.bubble.Message.SendAddFriend;
import com.wz.bubble.bubble.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.util.AndroidEmoji;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by BANBEICHAS on 2016/5/20.
 */
@ProviderTag(messageContent = SendAddFriend.class)
public class SendAddFriendItem extends IContainerItemProvider.MessageProvider<SendAddFriend> {

    @Override
    public void bindView(View view, int i, SendAddFriend sendAddFriend, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder)view.getTag();

        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        holder.message.setText(sendAddFriend.getContent());
        AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(SendAddFriend sendAddFriend) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, SendAddFriend sendAddFriend, UIMessage uiMessage) {

    }

    @Override
    public void onItemLongClick(View view, int i, SendAddFriend sendAddFriend, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_send_friend_add, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.tv_message_content);
        view.setTag(holder);
        return view;
    }
    class ViewHolder {
        TextView message;
    }
}
