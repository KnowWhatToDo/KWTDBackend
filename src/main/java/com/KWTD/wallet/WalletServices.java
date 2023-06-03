package com.KWTD.wallet;

import com.KWTD.wallet.Wallet;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class WalletServices {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    public String createWallet(Wallet wallet, String phone) throws InterruptedException, Exception {
        if (getWallet(phone) != null) {
            return "phone is null";
        }
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("wallet").document(phone).set(wallet);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Wallet getWallet(String phone) throws InterruptedException, Exception {
        DocumentReference documentReference = dbFirestore.collection("wallet").document(phone);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Wallet wallet;
        if (document.exists()) {
            wallet = document.toObject(Wallet.class);
            return wallet;
        } else {
            return null;
        }

    }

    public String updateWallet(Wallet wallet, String phone) throws InterruptedException, Exception {
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("wallet").document(phone).set(wallet);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteWallet(String phone) {
        ApiFuture<WriteResult> future = dbFirestore.collection("wallet").document(phone).delete();
        return "Document with Mentor Number " + phone + " has been deleted";
    }

}
