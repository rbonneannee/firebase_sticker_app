'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

/**
 * Triggers when a user receives a new message
 * The device notification tokens have been used to uniquely identify users
 *
 * Messages track the receiver `/messages/{messageUid}/{receiverToken}`
 * User documents are reached by `/users/{userToken}`
 */
exports.sendMessageNotification = functions.database.ref('/messages/{messageUid}')
  .onWrite(
    async (change, context) => {
      const messageUid = context.params.messageUid;
      functions.logger.log('LOGGER messageUid: ', messageUid);

      const messageSnapshot = await admin.database()
        .ref(`/messages/${messageUid}`)
        .once('value');
      const message = messageSnapshot.val();
      functions.logger.log('LOGGER message: ', message);

      const senderUsernameSnapshot = await admin.database()
        .ref(`/users/${message.senderToken}/username`)
        .once('value');
      const senderUsername = senderUsernameSnapshot.val();

      const payload = {
        notification: {
          title: `You got a sticker from ${senderUsername}!`
        },
        data: {
          sticker: `${message.stickerName}`
        }
      };
      functions.logger.log('LOGGER payload: ', payload);

      const options = {
        priority: 'high',
        timeToLive: 60 * 60 * 24
      }

      admin.messaging().sendToDevice(message.receiverToken, payload, options);
    });
