# bikeProject
Code of the bike project

### miglioramenti
- Fare le query con transazioni e rollback, per esempio ora quando finisce un abbonamento vengono fatte richieste al db diverse in moemnti diversi (una per chiudere la rack, un'altra per concludere l'abbonamento ecc...) se però in sequenza una delle query fallisce le altre sono già committate e non va bene.

- sincronizzazione per utilizzo da parte di diversi utenti nello stesso momento

- ho implementato una cosa sbagliata... l'utente alla sottoscrizione di un abbonamento deve aggiungere una carta di credito valida. Questa scelta però non la salvo in database quindi poi quando farò un noleggio detraggo il costo da una qualsiasi carta di credito dell'utente. Sono sicuro che una carta sia valida durante il noleggio però potrei aver problemi perchè il pagamento ricade su una qualsiasi carta valida dell'utente.

