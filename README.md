# bikeProject
Code of the bike project

### miglioramenti
- Fare le query con transazioni e rollback, per esempio ora quando finisce un abbonamento vengono fatte richieste al db diverse in moemnti diversi (una per chiudere la rack, un'altra per concludere l'abbonamento ecc...) se però in sequenza una delle query fallisce le altre sono già committate e non va bene.

- sincronizzazione per utilizzo da parte di diversi utenti nello stesso momento

