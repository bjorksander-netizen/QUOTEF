package com.bjorn.quotefilosofis

data class Quote(val text: String, val author: String, val school: String)

object Quotes {
    val SCHOOLS = listOf(
        "Optimisme", "Stoisisme", "Nihilisme", "Eksistensialisme", "Absurdisme", "Timur"
    )

    val ALL = listOf(
        // Optimisme
        Quote("Di tengah kesulitan selalu ada kesempatan.", "Albert Einstein", "Optimisme"),
        Quote("Kita hidup di dunia terbaik dari semua dunia yang mungkin.", "Gottfried Leibniz", "Optimisme"),
        Quote("Harapan adalah mimpi orang yang terjaga.", "Aristoteles", "Optimisme"),
        Quote("Apa yang tidak membunuhku membuatku lebih kuat.", "Friedrich Nietzsche", "Optimisme"),
        Quote("Kebahagiaan bergantung pada diri kita sendiri.", "Aristoteles", "Optimisme"),
        Quote("Satu-satunya batas untuk esok adalah keraguan kita hari ini.", "Franklin D. Roosevelt", "Optimisme"),

        // Stoisisme
        Quote("Kamu memiliki kuasa atas pikiranmu, bukan atas peristiwa di luar. Sadari ini, dan kamu akan menemukan kekuatan.", "Marcus Aurelius", "Stoisisme"),
        Quote("Bukan hal-hal yang mengganggu kita, melainkan penilaian kita atas hal-hal itu.", "Epictetus", "Stoisisme"),
        Quote("Kita lebih sering menderita dalam imajinasi daripada dalam kenyataan.", "Seneca", "Stoisisme"),
        Quote("Hambatan bagi tindakan memajukan tindakan. Yang menghalangi jalan menjadi jalan.", "Marcus Aurelius", "Stoisisme"),
        Quote("Kekayaan bukan pada banyaknya harta, tetapi pada sedikitnya keinginan.", "Epictetus", "Stoisisme"),
        Quote("Keberuntungan adalah saat persiapan bertemu kesempatan.", "Seneca", "Stoisisme"),

        // Nihilisme
        Quote("Jika kamu menatap jurang cukup lama, jurang itu balas menatapmu.", "Friedrich Nietzsche", "Nihilisme"),
        Quote("Tidak ada fakta, yang ada hanyalah tafsir.", "Friedrich Nietzsche", "Nihilisme"),
        Quote("Setiap keyakinan, setiap anggapan benar, pasti keliru: karena dunia yang benar tidak ada.", "Friedrich Nietzsche", "Nihilisme"),
        Quote("Hidup tidak memiliki makna bawaan — dan justru itu membebaskanmu untuk menciptakannya.", "Anonim", "Nihilisme"),
        Quote("Kekacauan adalah tetangga Tuhan yang paling dekat.", "Emil Cioran", "Nihilisme"),

        // Eksistensialisme
        Quote("Manusia dikutuk untuk bebas; karena sekali dilempar ke dunia, ia bertanggung jawab atas semua yang ia lakukan.", "Jean-Paul Sartre", "Eksistensialisme"),
        Quote("Eksistensi mendahului esensi.", "Jean-Paul Sartre", "Eksistensialisme"),
        Quote("Dia yang memiliki alasan untuk hidup dapat menanggung hampir segala cara.", "Friedrich Nietzsche", "Eksistensialisme"),
        Quote("Kecemasan adalah pusing kebebasan.", "Soren Kierkegaard", "Eksistensialisme"),
        Quote("Hidup hanya bisa dipahami ke belakang; tetapi harus dijalani ke depan.", "Soren Kierkegaard", "Eksistensialisme"),
        Quote("Jadilah dirimu sendiri; semua orang lain sudah ada yang punya.", "Oscar Wilde", "Eksistensialisme"),

        // Absurdisme
        Quote("Di tengah musim dingin, akhirnya kutemukan bahwa dalam diriku ada musim panas yang tak terkalahkan.", "Albert Camus", "Absurdisme"),
        Quote("Kita harus membayangkan Sisifus bahagia.", "Albert Camus", "Absurdisme"),
        Quote("Satu-satunya cara menghadapi dunia yang tidak bebas adalah menjadi begitu bebas hingga keberadaanmu sendiri adalah pemberontakan.", "Albert Camus", "Absurdisme"),
        Quote("Hidup akan dijalani lebih baik jika ia tidak memiliki makna.", "Albert Camus", "Absurdisme"),
        Quote("Kebahagiaan dan absurd adalah dua anak dari bumi yang sama. Keduanya tak terpisahkan.", "Albert Camus", "Absurdisme"),

        // Filsafat Timur
        Quote("Perjalanan seribu mil dimulai dengan satu langkah.", "Lao Tzu", "Timur"),
        Quote("Saat kamu melepaskan siapa dirimu, kamu menjadi siapa yang mungkin kamu jadi.", "Lao Tzu", "Timur"),
        Quote("Akar penderitaan adalah kemelekatan.", "Buddha", "Timur"),
        Quote("Pikiran adalah segalanya. Apa yang kamu pikirkan, itulah kamu.", "Buddha", "Timur"),
        Quote("Air yang mengalir tidak pernah basi. Teruslah bergerak.", "Bruce Lee", "Timur"),
        Quote("Dia yang mengenal orang lain itu bijak; dia yang mengenal dirinya sendiri itu tercerahkan.", "Lao Tzu", "Timur")
    )

    fun random(schools: Set<String>): Quote {
        val pool = if (schools.isEmpty()) ALL else ALL.filter { it.school in schools }
        return (pool.ifEmpty { ALL }).random()
    }
}
