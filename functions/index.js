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
      let receiverToken;
      const stupidPromise = admin.database()
        .ref(`/messages/${messageUid}/receiverToken`)
        .once('value')
        .then((snapshot) => {
          functions.logger.log('LOGGER snapshot: ', snapshot);
          receiverToken = snapshot.val();
          return snapshot.val(); // Super misleading - `.then()` returns a Promise
        });

      // TODO figure out if it's a problem with this or the deprecated getToken()
      functions.logger.log('LOGGER receiverToken: ', receiverToken);

      const payload = {
        notification: {
          title: 'You got a sticker!'
        }
      };
      functions.logger.log('LOGGER payload: ', payload);

      admin.messaging().sendToDevice(receiverToken, payload);
    });
