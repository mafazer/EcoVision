package com.example.ecovision

object PlasticData {
    val plasticTypes = listOf(
        PlasticType(
            name = "PET",
            imageResId = R.drawable.pet_image,
            imageIcon = R.drawable.ic_pet,
            recyclingCode = "PET",
            description = "PET (Polyethylene Terephthalate) adalah jenis plastik yang sering digunakan untuk botol minuman dan kemasan makanan karena ringan, kuat, dan transparan. Plastik ini dapat didaur ulang dan diubah menjadi berbagai produk baru.",
            recyclingProcess = "Plastik PET dapat dikumpulkan, dicuci, dipotong kecil-kecil, dilebur, dan dibentuk kembali menjadi produk baru seperti serat untuk pakaian atau karpet.",
            environmentalImpact = "Waktu terurai: 450 tahun. Plastik jenis PET sangat tahan lama di alam dan bisa mencemari lingkungan, terutama lautan.",
            examples = listOf(R.drawable.pet_image, R.drawable.pet_image)
        ),
        PlasticType(
            name = "HDPE",
            imageResId = R.drawable.hdpe_image,
            imageIcon = R.drawable.ic_hdpe,
            recyclingCode = "HDPE",
            description = "HDPE (High-Density Polyethylene) adalah jenis plastik yang kuat dan tahan lama, sering digunakan untuk botol susu, botol deterjen, dan pipa.",
            recyclingProcess = "Plastik HDPE dapat didaur ulang dengan cara dicuci, dipotong kecil-kecil, dilebur, dan dibentuk menjadi produk baru seperti pipa dan bahan konstruksi.",
            environmentalImpact = "Waktu terurai: 500 tahun. Plastik HDPE dapat menyebabkan polusi jika tidak didaur ulang dengan benar.",
            examples = listOf(R.drawable.hdpe_image, R.drawable.hdpe_image)
        ),
        PlasticType(
            name = "PVC",
            imageResId = R.drawable.pvc_image,
            imageIcon = R.drawable.ic_pvc,
            recyclingCode = "PVC",
            description = "PVC (Polyvinyl Chloride) adalah plastik yang sering digunakan untuk pipa, kabel, dan bahan konstruksi karena tahan terhadap cuaca dan bahan kimia.",
            recyclingProcess = "Plastik PVC dapat didaur ulang melalui proses pemanasan dan pembentukan ulang, namun proses ini lebih sulit dibanding jenis plastik lainnya.",
            environmentalImpact = "Waktu terurai: 1000 tahun. PVC mengandung klorin yang dapat menyebabkan polusi jika dibakar.",
            examples = listOf(R.drawable.pvc_image, R.drawable.pvc_image)
        ),
        PlasticType(
            name = "LDPE",
            imageResId = R.drawable.ldpe_image,
            imageIcon = R.drawable.ic_ldpe,
            recyclingCode = "LDPE",
            description = "LDPE (Low-Density Polyethylene) adalah plastik yang sering digunakan untuk kantong plastik, bungkus makanan, dan botol yang dapat diremas.",
            recyclingProcess = "Plastik LDPE dapat didaur ulang dengan cara dicuci, dipotong kecil-kecil, dilebur, dan dibentuk menjadi produk baru seperti kantong sampah dan ubin lantai.",
            environmentalImpact = "Waktu terurai: 500-1000 tahun. Plastik LDPE dapat menyebabkan polusi plastik di lautan dan daratan.",
            examples = listOf(R.drawable.ldpe_image, R.drawable.ldpe_image)
        ),
        PlasticType(
            name = "PP",
            imageResId = R.drawable.pp_image,
            imageIcon = R.drawable.ic_pp,
            recyclingCode = "PP",
            description = "PP (Polypropylene) adalah jenis plastik yang sering digunakan untuk kemasan makanan, sedotan, dan produk otomotif.",
            recyclingProcess = "Plastik PP dapat didaur ulang dengan cara dicuci, dipotong kecil-kecil, dilebur, dan dibentuk menjadi produk baru seperti kontainer dan mainan.",
            environmentalImpact = "Waktu terurai: 20-30 tahun. Plastik PP lebih mudah didaur ulang dibandingkan beberapa jenis plastik lainnya.",
            examples = listOf(R.drawable.pp_image, R.drawable.pp_image)
        ),
        PlasticType(
            name = "PS",
            imageResId = R.drawable.ps_image,
            imageIcon = R.drawable.ic_ps,
            recyclingCode = "PS",
            description = "PS (Polystyrene) adalah plastik yang sering digunakan untuk wadah makanan sekali pakai, cangkir, dan kemasan pelindung.",
            recyclingProcess = "Plastik PS dapat didaur ulang melalui proses pemanasan dan pembentukan ulang, namun biasanya tidak didaur ulang karena murah dan tidak ekonomis.",
            environmentalImpact = "Waktu terurai: 500 tahun. Plastik PS dapat menyebabkan polusi lingkungan dan berbahaya bagi kehidupan laut.",
            examples = listOf(R.drawable.ps_image, R.drawable.ps_image)
        ),
        PlasticType(
            name = "Other",
            imageResId = R.drawable.other_image,
            imageIcon = R.drawable.ic_other,
            recyclingCode = "Other",
            description = "Kategori ini mencakup semua jenis plastik lainnya yang tidak termasuk dalam kategori di atas. Ini bisa termasuk plastik berbasis biologi dan campuran plastik.",
            recyclingProcess = "Proses daur ulang bervariasi tergantung pada jenis plastiknya.",
            environmentalImpact = "Dampak lingkungan sangat bervariasi tergantung pada jenis plastiknya.",
            examples = listOf(R.drawable.other_image, R.drawable.other_image)
        )
    )
}