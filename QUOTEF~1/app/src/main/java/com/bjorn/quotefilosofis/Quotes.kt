package com.bjorn.quotefilosofis

data class Quote(
    val textId: String,
    val textEn: String,
    val author: String,
    val school: String
)

fun Quote.displayText(lang: String) = if (lang == "en") textEn else textId

fun schoolLabel(school: String, lang: String) = when (school) {
    "Optimisme"        -> if (lang == "en") "Optimism"        else "Optimisme"
    "Stoisisme"        -> if (lang == "en") "Stoicism"        else "Stoisisme"
    "Nihilisme"        -> if (lang == "en") "Nihilism"        else "Nihilisme"
    "Eksistensialisme" -> if (lang == "en") "Existentialism"  else "Eksistensialisme"
    "Absurdisme"       -> if (lang == "en") "Absurdism"       else "Absurdisme"
    "Timur"            -> if (lang == "en") "Eastern"         else "Timur"
    else               -> school
}

val ALL_QUOTES = listOf(
    // ══════════════════════════════════════════════════════════════════════════════
    //  OPTIMISME  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Segalanya berakhir dengan baik pada akhirnya. Jika belum baik, itu belum akhirnya.",
        "Everything will be okay in the end. If it's not okay, it's not the end.",
        "Oscar Wilde", "Optimisme"
    ),
    Quote(
        "Optimisme adalah keyakinan yang mengarah pada pencapaian. Tidak ada yang bisa dilakukan tanpa harapan.",
        "Optimism is the faith that leads to achievement. Nothing can be done without hope.",
        "Helen Keller", "Optimisme"
    ),
    Quote(
        "Di tengah setiap kesulitan tersimpan kesempatan.",
        "In the middle of every difficulty lies opportunity.",
        "Albert Einstein", "Optimisme"
    ),
    Quote(
        "Kita harus menerima kekecewaan yang terbatas, tapi tidak pernah kehilangan harapan yang tak terbatas.",
        "We must accept finite disappointment, but never lose infinite hope.",
        "Martin Luther King Jr.", "Optimisme"
    ),
    Quote(
        "Masa depan adalah milik mereka yang percaya akan keindahan impian mereka.",
        "The future belongs to those who believe in the beauty of their dreams.",
        "Eleanor Roosevelt", "Optimisme"
    ),
    Quote(
        "Tetaplah berharap. Karena bahkan matahari pun terbenam di malam hari dan terbit lagi di pagi hari.",
        "Keep hope alive. For even the sun sets at night and rises again in the morning.",
        "Rumi", "Optimisme"
    ),
    Quote(
        "Kegagalan adalah kesempatan untuk memulai lagi dengan lebih bijaksana.",
        "Failure is the opportunity to begin again, more intelligently.",
        "Henry Ford", "Optimisme"
    ),
    Quote(
        "Pikiranmu adalah pot. Isi dengan harapan, bukan keputusasaan.",
        "Your mind is a garden, your thoughts are the seeds. You can grow flowers or you can grow weeds.",
        "Rumi", "Optimisme"
    ),
    Quote(
        "Kita adalah apa yang kita pikirkan berulang kali. Dengan pemikiran itulah kita menciptakan diri sendiri.",
        "We are what we repeatedly do. Excellence, then, is not an act, but a habit.",
        "Aristoteles", "Optimisme"
    ),
    Quote(
        "Satu-satunya batasan untuk kesuksesanmu besok adalah keraguanmu hari ini.",
        "The only limit to your impact is your imagination and commitment today.",
        "Tony Robbins", "Optimisme"
    ),
    Quote(
        "Yang dibutuhkan dunia adalah orang-orang yang tidak bisa dihentikan.",
        "What the world needs is people who cannot be stopped.",
        "Moral Kekinian", "Optimisme"
    ),
    Quote(
        "Setiap hari mungkin tidak baik, tapi ada sesuatu yang baik di setiap hari.",
        "Every day may not be good, but there is something good in every day.",
        "Alice Morse Earle", "Optimisme"
    ),
    Quote(
        "Jangan tunggu kondisi sempurna untuk mulai. Mulailah dan kondisi akan membaik seiring jalan.",
        "Don't wait for conditions to be perfect to start. Start and conditions will improve along the way.",
        "Napoleon Hill", "Optimisme"
    ),
    Quote(
        "Kebahagiaan bukan sesuatu yang sudah jadi. Kebahagiaan berasal dari tindakanmu sendiri.",
        "Happiness is not something ready-made. It comes from your own actions.",
        "Dalai Lama", "Optimisme"
    ),
    Quote(
        "Yang membuat kita kuat bukanlah apa yang kita miliki, tapi apa yang kita relakan.",
        "What makes us strong is not what we have, but what we are willing to let go.",
        "Kahlil Gibran", "Optimisme"
    ),
    Quote(
        "Ketika satu pintu tertutup, pintu lain terbuka. Tapi kita sering menatap pintu yang tertutup begitu lama sehingga kita tidak melihat pintu yang telah terbuka.",
        "When one door closes, another opens; but we often look so long and so regretfully upon the closed door that we do not see the one which has opened for us.",
        "Alexander Graham Bell", "Optimisme"
    ),
    Quote(
        "Cahaya tidak bisa ada tanpa kegelapan. Tapi kegelapan tidak bisa menelan cahaya.",
        "Light cannot exist without darkness. But darkness cannot swallow light.",
        "Moral Kekinian", "Optimisme"
    ),
    Quote(
        "Berusahalah untuk bukan menjadi orang yang sukses, melainkan orang yang bernilai.",
        "Strive not to be a success, but rather to be of value.",
        "Albert Einstein", "Optimisme"
    ),
    Quote(
        "Kamu tidak bisa kembali dan mengubah awal ceritamu, tapi kamu bisa mulai dari sini dan mengubah akhir ceritamu.",
        "You can't go back and change the beginning, but you can start where you are and change the ending.",
        "C.S. Lewis", "Optimisme"
    ),
    Quote(
        "Yang paling indah dari semua hal di dunia ini tidak dapat dilihat atau disentuh, tapi harus dirasakan dengan hati.",
        "The best and most beautiful things in the world cannot be seen or even touched, they must be felt with the heart.",
        "Helen Keller", "Optimisme"
    ),
    Quote(
        "Jika kamu menjatuhkan diri di tanah yang benar, kamu akan tumbuh seperti pohon yang besar.",
        "If you fall on the right ground, you will grow into a great tree.",
        "Napoleon Hill", "Optimisme"
    ),
    Quote(
        "Hidup ini 10% apa yang terjadi padamu dan 90% bagaimana kamu meresponsnya.",
        "Life is 10% what happens to you and 90% how you respond to it.",
        "Charles R. Swindoll", "Optimisme"
    ),
    Quote(
        "Orang hebat tidak dilahirkan, mereka dibentuk oleh tantangan.",
        "Great people are not born, they are shaped by challenges.",
        "Moral Kekinian", "Optimisme"
    ),
    Quote(
        "Ketika kamu merasa lelah, ingatlah mengapa kamu memulai.",
        "When you feel tired, remember why you started.",
        "Moral Kekinian", "Optimisme"
    ),
    Quote(
        "Setiap pencapaian besar dimulai dengan keputusan untuk mencoba.",
        "Every great achievement starts with the decision to try.",
        "John F. Kennedy", "Optimisme"
    ),

    // ══════════════════════════════════════════════════════════════════════════════
    //  STOISISME  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Hambatan bagi tindakan justru mendorong tindakan. Apa yang menghalangi, menjadi jalannya.",
        "The impediment to action advances action. What stands in the way becomes the way.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Kamu memiliki kuasa atas pikiranmu, bukan peristiwa luar. Sadari ini, dan kamu akan menemukan kekuatan.",
        "You have power over your mind, not outside events. Realize this, and you will find strength.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Bukan apa yang terjadi padamu, tapi bagaimana kamu bereaksi terhadapnya yang penting.",
        "It's not what happens to you, but how you react to it that matters.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Manfaatkan sebaik-baiknya apa yang ada dalam kekuasaanmu, dan terima sisanya sebagaimana adanya.",
        "Make the best use of what is in your power, and take the rest as it happens.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Kita lebih sering menderita dalam imajinasi daripada dalam kenyataan.",
        "We suffer more often in imagination than in reality.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Keberuntungan adalah apa yang terjadi ketika persiapan bertemu kesempatan.",
        "Luck is what happens when preparation meets opportunity.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Jangan membuang-buang waktu berdebat tentang seperti apa manusia yang baik itu. Jadilah itu.",
        "Waste no more time arguing about what a good man should be. Be one.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Selalu bayangkan bahwa kamu akan mati besok. Hal itu akan membuatmu berusaha untuk melakukan yang terbaik hari ini.",
        "Always think that you will die tomorrow. It will make you strive to do your best today.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Hidup ini cukup panjang jika kamu tahu cara menggunakannya.",
        "Life is long enough if you know how to use it.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Kekayaan bukanlah memiliki kemewahan, tapi memiliki kekurangan yang sedikit.",
        "Wealth is not about having a lot of money, but having a lot of少.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Kebenaran tidak mengenal keraguan. Keraguan mengenal kebenaran.",
        "Truth does not admit doubt. Doubt does not admit truth.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Tidak ada yang bebas kecuali pikiran seseorang.",
        "No man is free except the one who controls his own mind.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Kita lahir tanpa membawa apa-apa, kita pergi tanpa membawa apa-apa.",
        "We are born with nothing, we leave with nothing.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Barangsiapa mengendalikan dirinya, ia mengendalikan dunia.",
        "He who conquers himself is the mightiest warrior.",
        "Lao Tzu", "Stoisisme"
    ),
    Quote(
        "Kebijaksanaan sejati adalah mengetahui seberapa sedikit yang kamu ketahui.",
        "True wisdom is knowing how little you know.",
        "Socrates", "Stoisisme"
    ),
    Quote(
        "Jangan khawatir tentang apa yang tidak bisa kamu kendalikan. Fokus pada apa yang bisa.",
        "Don't worry about what you can't control. Focus on what you can.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Waktu seperti pedang bermata dua. Jika kamu tidak menggunakannya dengan bijak, ia akan menggunakannya untukmu.",
        "Time is like a double-edged sword. If you don't use it wisely, it will use you.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Hidupmu adalah pikiranmu. Ubah pikiranmu, ubah hidupmu.",
        "Your life is your mind. Change your mind, change your life.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Kita semua menderita. Tapi yang bijak menderita dengan cara yang benar.",
        "We all suffer. But the wise suffer in the right way.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Tidak ada yang lebih berharga daripada kedamaian batin.",
        "Nothing is more valuable than inner peace.",
        "Marcus Aurelius", "Stoisisme"
    ),
    Quote(
        "Kebebasan sejati adalah kebebasan dari keinginan.",
        "True freedom is freedom from desire.",
        "Epiktetos", "Stoisisme"
    ),
    Quote(
        "Jika kamu ingin menjadi bijaksana, mulailah dengan meragukan apa yang kamu anggap pasti.",
        "If you want to be wise, begin by doubting what you consider certain.",
        "Socrates", "Stoisisme"
    ),
    Quote(
        "Hidup yang tidak diperiksa tidak layak dijalani.",
        "The unexamined life is not worth living.",
        "Socrates", "Stoisisme"
    ),
    Quote(
        "Kamu bisa menunda, tapi waktu tidak akan menunggumu.",
        "You may delay, but time will not.",
        "Seneca", "Stoisisme"
    ),
    Quote(
        "Kekuatan terbesar adalah ketenangan.",
        "The greatest strength is calmness.",
        "Marcus Aurelius", "Stoisisme"
    ),

    // ══════════════════════════════════════════════════════════════════════════════
    //  NIHILISME  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Tuhan sudah mati. Tuhan tetap mati. Dan kita yang telah membunuhnya.",
        "God is dead. God remains dead. And we have killed him.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Tanpa musik, hidup akan menjadi sebuah kesalahan.",
        "Without music, life would be a mistake.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Yang tidak membunuhmu akan membuatmu lebih kuat.",
        "That which does not kill us, makes us stronger.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Dia yang punya alasan untuk hidup dapat menanggung hampir segalanya.",
        "He who has a why to live can bear almost any how.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Tidak ada fakta, hanya interpretasi.",
        "There are no facts, only interpretations.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Untuk hidup adalah menderita, untuk bertahan adalah menemukan makna dalam penderitaan.",
        "To live is to suffer, to survive is to find some meaning in the suffering.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Manusia tidak mencari kebahagiaan, hanya orang Inggris yang melakukan itu.",
        "Man does not seek happiness, only the English do that.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Yang paling berbahaya adalah kebohongan yang kita yakini sebagai kebenaran.",
        "The most dangerous lies are the ones we believe to be true.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Ada fakta狂乱 fakta狂乱, tapi tidak ada kebenaran.",
        "There are many facts, but no truth.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Setiap konsep yang muncul setelah sesuatu yang terjadi selalu salah.",
        "Every concept that arises after something has happened is always wrong.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Hati-hati dengan mereka yang memiliki keyakinan kuat tentang hal-hal yang tidak mereka pahami.",
        "Beware of those who have strong convictions about things they don't understand.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Kita harus menjadi laut yang dalam untuk menelan sungai yang kotor tanpa menjadi kotor.",
        "We must be a deep sea to swallow the dirty river without becoming dirty.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Tidak ada yang lebih menyedihkan daripada kebenaran yang tidak berguna.",
        "There is nothing sadder than a truth that is useless.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Setiap manusia adalah eksperimen yang unik.",
        "Every human being is a unique experiment.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Kebenaran tidak pernah indah. Indahlah yang menjadi kebenaran.",
        "Truth is never pure, and rarely simple.",
        "Oscar Wilde", "Nihilisme"
    ),
    Quote(
        "Hidup tidak memiliki makna selain yang kamu berikan padanya.",
        "Life has no meaning except the one you give it.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Kita tidak melihat segalanya apa adanya. Kita melihat segalanya seperti kita.",
        "We don't see things as they are, we see things as we are.",
        "Anaïs Nin", "Nihilisme"
    ),
    Quote(
        "Makna hidup adalah pertanyaan yang salah. Yang benar adalah bagaimana kamu hidup.",
        "The meaning of life is the wrong question. The right one is how you live.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Kebosanan adalah perasaan bahwa tidak ada yang menarik.",
        "Boredom is the feeling that nothing is worth feeling.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Jika kamu mencari makna di dunia yang tidak memiliki makna, kamu akan menemukan kegilaan.",
        "If you seek meaning in a meaningless world, you will find madness.",
        "Albert Camus", "Nihilisme"
    ),
    Quote(
        "Kita terlahir dalam kehampaan. Kita hidup di antara kehampaan. Kita mati dalam kehampaan.",
        "We are born into the void. We live between voids. We die into the void.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Tidak ada yang sakral, kecuali kehormatan masing-masing individu.",
        "Nothing is sacred except the individuality of each person.",
        "Friedrich Nietzsche", "Nihilisme"
    ),
    Quote(
        "Hidup itu sendiri tidak memiliki tujuan. Kita harus menciptakannya sendiri.",
        "Life itself has no purpose. We must create it ourselves.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Kita semua adalah pengembara yang mencari jawaban untuk pertanyaan yang tidak ada.",
        "We are all wanderers searching for answers to questions that don't exist.",
        "Moral Kekinian", "Nihilisme"
    ),
    Quote(
        "Setelah kegelapan terbesar, datanglah kehampaan terbesar.",
        "After the greatest darkness, comes the greatest emptiness.",
        "Friedrich Nietzsche", "Nihilisme"
    ),

    // ══════════════════════════════════════════════════════════════════════════════
    //  EKSISTENSIALISME  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Eksistensi mendahului esensi.",
        "Existence precedes essence.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Manusia dikutuk untuk menjadi bebas.",
        "Man is condemned to be free.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Neraka adalah orang lain.",
        "Hell is other people.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Kamu tidak akan pernah bahagia jika terus mencari apa itu kebahagiaan.",
        "You will never be happy if you continue to search for what happiness consists of.",
        "Albert Camus", "Eksistensialisme"
    ),
    Quote(
        "Seseorang tidak dilahirkan sebagai wanita, melainkan menjadi seorang wanita.",
        "One is not born, but rather becomes, a woman.",
        "Simone de Beauvoir", "Eksistensialisme"
    ),
    Quote(
        "Hidup hanya bisa dipahami ke belakang, tapi harus dijalani ke depan.",
        "Life can only be understood backwards, but it must be lived forwards.",
        "Søren Kierkegaard", "Eksistensialisme"
    ),
    Quote(
        "Orang yang paling putus asa adalah orang yang pernah mengalami kebebasan paling banyak.",
        "The most desperate people are those who have experienced the most freedom.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Manusia adalah makhluk yang dilemparkan ke dunia.",
        "Man is a being thrown into the world.",
        "Martin Heidegger", "Eksistensialisme"
    ),
    Quote(
        "Ketakutan mengungkapkan makna hidup yang sesungguhnya.",
        "Fear reveals the true meaning of life.",
        "Martin Heidegger", "Eksistensialisme"
    ),
    Quote(
        "Kesadaran adalah kesadaran akan sesuatu.",
        "Consciousness is consciousness of something.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Kita tidak memiliki esensi sebelum kita exist.",
        "We have no essence before we exist.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Kebebasan adalah beban yang berat.",
        "Freedom is a heavy burden.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Orang-orang terasing tidak selalu diasingkan, tapi selalu merasa asing.",
        "Alienated people are not always alienated, but always feel alien.",
        "Albert Camus", "Eksistensialisme"
    ),
    Quote(
        "Pilihan kita menentukan siapa kita. Kita tidak bisa menyalahkan keadaan.",
        "Our choices determine who we. We cannot blame circumstances.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Ketika tidak ada Tuhan, semua diperbolehkan.",
        "When there is no God, everything is permitted.",
        "Fyodor Dostoevsky", "Eksistensialisme"
    ),
    Quote(
        "Tidak ada makna yang sudah diberikan. Kita harus menciptakannya sendiri.",
        "There is no given meaning. We must create it ourselves.",
        "Albert Camus", "Eksistensialisme"
    ),
    Quote(
        "Seseorang tidak bisa menemukan kebenaran tanpa menemukan kebebasan.",
        "One cannot find truth without finding freedom.",
        "Simone de Beauvoir", "Eksistensialisme"
    ),
    Quote(
        "Hidup itu absurd, tapi itu tidak berarti hidup tidak berharga.",
        "Life is absurd, but that doesn't mean life is not worth living.",
        "Albert Camus", "Eksistensialisme"
    ),
    Quote(
        "Tanggung jawab adalah konsekuensi dari kebebasan.",
        "Responsibility is the consequence of freedom.",
        "Jean-Paul Sartre", "Eksistensialisme"
    ),
    Quote(
        "Kematian adalah satu-satunya kepastian hidup.",
        "Death is the only certainty of life.",
        "Martin Heidegger", "Eksistensialisme"
    ),
    Quote(
        "Kamu tidak bisa menemukan dirimu sendiri dengan mengikuti orang lain.",
        "You cannot find yourself by following others.",
        "Søren Kierkegaard", "Eksistensialisme"
    ),
    Quote(
        "Kecemasan adalah kemerdekaan yang menyadari dirinya sendiri.",
        "Anxiety is freedom's self-awareness.",
        "Søren Kierkegaard", "Eksistensialisme"
    ),
    Quote(
        "Hidup harus dijalani, bukan dianalisis.",
        "Life must be lived, not analyzed.",
        "Albert Camus", "Eksistensialisme"
    ),
    Quote(
        "Kita semua adalah makhluk yang mencari makna di dunia yang tidak mencari makna.",
        "We are all beings seeking meaning in a world that doesn't seek meaning.",
        "Viktor Frankl", "Eksistensialisme"
    ),
    Quote(
        "Penderitaan berhenti menjadi penderitaan pada saat ia menemukan makna.",
        "Suffering ceases to be suffering the moment it finds a meaning.",
        "Viktor Frankl", "Eksistensialisme"
    ),

    // ══════════════════════════════════════════════════════════════════════════════
    //  ABSURDISME  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Seseorang harus membayangkan Sisifus bahagia.",
        "One must imagine Sisyphus happy.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Di kedalaman musim dingin, aku akhirnya menyadari bahwa di dalam diriku terdapat musim panas yang tak terkalahkan.",
        "In the depth of winter, I finally learned that within me there lay an invincible summer.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Satu-satunya cara menghadapi dunia yang tidak bebas adalah dengan menjadi begitu bebas sehingga keberadaanmu sendiri adalah pemberontakan.",
        "The only way to deal with an unfree world is to become so absolutely free that your very existence is an act of rebellion.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Absurd lahir dari konfrontasi antara kebutuhan manusia dan keheningan dunia yang tak masuk akal.",
        "The absurd is born from the confrontation between the human need and the unreasonable silence of the world.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Hidup adalah petualangan yang berani atau bukan apa-apa.",
        "Life is either a daring adventure or nothing at all.",
        "Helen Keller", "Absurdisme"
    ),
    Quote(
        "Hidup adalah seni yang paling sulit. Kita harus hidup tanpa menguji kenyataan.",
        "Living is the most difficult of arts. We must live without testing reality.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Jangan berjalan di depanku, aku mungkin tidak mengikuti. Jangan berjalan di belakangku, aku mungkin tidak memimpin. Berjalanlah di sampingku dan jadilah temanku.",
        "Don't walk in front of me, I may not follow. Don't walk behind me, I may not lead. Just walk beside me and be my friend.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Revolusi tidak terjadi ketika masyarakat harus diberi makan, tapi ketika mereka harus diberi makna.",
        "Revolution is not when society must be fed, but when they must be given meaning.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Saya menolak untuk percaya bahwa hidup adalah absurd.",
        "I refuse to believe that life is absurd.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Kekacauan bukan ketiadaan aturan. Kekacauan adalah aturan yang belum ditemukan.",
        "Chaos is not the absence of rules. Chaos is the rule that has not yet been found.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Setiap hari kita dilahirkan kembali. Yang kita lakukan hari ini adalah yang paling penting.",
        "Every day we are born again. What we do today is what matters most.",
        "Buddha", "Absurdisme"
    ),
    Quote(
        "Jika kamu mencari kebenaran, kamu harus siap untuk hidup tanpa kenyamanan.",
        "If you seek truth, you must be prepared to live without comfort.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Kita harus membayangkan Sisifus bahagia. Kita harus membayangkan bahwa kita bahagia.",
        "We must imagine Sisyphus happy. We must imagine that we are happy.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Hidup itu seperti mengendarai sepeda. Untuk menjaga keseimbangan, kamu harus terus bergerak.",
        "Life is like riding a bicycle. To keep your balance, you must keep moving.",
        "Albert Einstein", "Absurdisme"
    ),
    Quote(
        "Hidup bukan tentang menunggu badai berlalu. Hidup adalah tentang belajar menari di tengah hujan.",
        "Life is not about waiting for the storm to pass. Life is about learning to dance in the rain.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Setiap pilihan adalah pengorbanan. Kita harus belajar hidup dengan pengorbanan kita.",
        "Every choice is a sacrifice. We must learn to live with our sacrifices.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Kita semua sedang berjalan di kuburan. Pertanyaannya adalah, bagaimana kita berjalan.",
        "We are all walking to the grave. The question is, how we walk.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Ketika dunia memberikanmu alasan untuk menangis, tunjukkan dunia alasan untuk tersenyum.",
        "When the world gives you a reason to cry, show the world a reason to smile.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Kehidupan ini singkat untuk dihabiskan dengan membenci.",
        "Life is too short to be spent hating.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Kita harus memeluk absurditas dan hidup dengan penuh semangat.",
        "We must embrace the absurd and live passionately.",
        "Albert Camus", "Absurdisme"
    ),
    Quote(
        "Hidup adalah pertunjukan yang aneh, penuh dengan kejutan yang tidak terduga.",
        "Life is a strange show, full of unexpected surprises.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Tidak ada yang lebih real dari mimpi kita.",
        "Nothing is more real than our dreams.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Kita tidak hidup untuk mati. Kita hidup untuk hidup.",
        "We don't live to die. We live to live.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Dunia ini indah, tapi kita harus mencarinya.",
        "The world is beautiful, but we must seek it.",
        "Moral Kekinian", "Absurdisme"
    ),
    Quote(
        "Hidup itu aneh. Kita harus hidup dengan keanehannya.",
        "Life is strange. We must live with its strangeness.",
        "Albert Camus", "Absurdisme"
    ),

    // ══════════════════════════════════════════════════════════════════════════════
    //  TIMUR  (25 quotes)
    // ══════════════════════════════════════════════════════════════════════════════
    Quote(
        "Perjalanan seribu mil dimulai dari satu langkah.",
        "The journey of a thousand miles begins with a single step.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Mengenal orang lain adalah kecerdasan; mengenal dirimu sendiri adalah kebijaksanaan sejati.",
        "Knowing others is intelligence; knowing yourself is true wisdom.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Pikiran adalah segalanya. Apa yang kamu pikirkan, itulah yang kamu menjadi.",
        "The mind is everything. What you think, you become.",
        "Buddha", "Timur"
    ),
    Quote(
        "Kedamaian datang dari dalam. Jangan mencarinya di luar.",
        "Peace comes from within. Do not seek it without.",
        "Buddha", "Timur"
    ),
    Quote(
        "Tidak masalah seberapa lambat kamu pergi, selama kamu tidak berhenti.",
        "It does not matter how slowly you go as long as you do not stop.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Hidup sebenarnya sederhana, tapi kita bersikeras mempersulit diri.",
        "Life is really simple, but we insist on making it complicated.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Di tengah kekacauan, ada juga kesempatan.",
        "In the midst of chaos, there is also opportunity.",
        "Sun Tzu", "Timur"
    ),
    Quote(
        "Ketika kamu menyadari tidak ada yang kurang, seluruh dunia menjadi milikmu.",
        "When you realize there is nothing lacking, the whole world belongs to you.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Jangan berjuang melawan apa yang tidak bisa kamu ubah. Terima dan bebaskan dirimu.",
        "Do not struggle against what you cannot change. Accept and free yourself.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Kekuatan terletak dalam kelembutan, bukan dalam kekerasan.",
        "Strength lies in gentleness, not in violence.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Kebijaksanaan adalah mengetahui apa yang tidak kamu ketahui.",
        "Wisdom is knowing what you do not know.",
        "Socrates", "Timur"
    ),
    Quote(
        "Pikiran yang tenang menghasilkan tindakan yang tepat.",
        "A calm mind produces the right action.",
        "Buddha", "Timur"
    ),
    Quote(
        "Jika kamu mencari kebenaran, kamu harus siap untuk hidup tanpa kenyamanan.",
        "If you seek truth, you must be prepared to live without comfort.",
        "Buddha", "Timur"
    ),
    Quote(
        "Semua makhluk hidup merasakan penderitaan. Penderitaan berakhir ketika keinginan berakhir.",
        "All sentient beings suffer. Suffering ends when desire ends.",
        "Buddha", "Timur"
    ),
    Quote(
        "Air melalui jalan yang paling rendah. Tapi itu adalah air yang paling kuat.",
        "Water flows to the lowest place. But it is the strongest water.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Jangan bandingkan dirimu dengan orang lain. Bandingkan dirimu dengan dirimu sendiri di masa lalu.",
        "Don't compare yourself with others. Compare yourself with yourself of the past.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Ilmu yang tidak diamalkan adalah ilmu yang sia-sia.",
        "Knowledge that is not practiced is useless knowledge.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Waktu yang paling berharga adalah waktu yang kita habiskan untuk kebaikan.",
        "The most valuable time is the time we spend doing good.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Orang bijaksana tidak menunjukkan kebijaksanaannya di depan semua orang.",
        "The wise do not show their wisdom in front of everyone.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Ketenangan adalah kekuatan sejati.",
        "Tranquility is true strength.",
        "Lao Tzu", "Timur"
    ),
    Quote(
        "Kita tidak bisa mengendalikan angin, tapi kita bisa mengatur layar.",
        "We cannot control the wind, but we can adjust the sails.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Ketika kamu bijaksana, kamu akan memahami banyak hal tanpa perlu menjelaskan.",
        "When you are wise, you will understand many things without explanation.",
        "Konfusius", "Timur"
    ),
    Quote(
        "Kebijaksanaan sejati adalah mengetahui kapan harus berbicara dan kapan harus diam.",
        "True wisdom is knowing when to speak and when to remain silent.",
        "Sun Tzu", "Timur"
    ),
    Quote(
        "Ketenangan batin adalah kunci kebahagiaan.",
        "Inner peace is the key to happiness.",
        "Buddha", "Timur"
    ),
    Quote(
        "Jangan terburu-buru. Waktu yang tepat akan datang.",
        "Don't rush. The right time will come.",
        "Lao Tzu", "Timur"
    )
)
