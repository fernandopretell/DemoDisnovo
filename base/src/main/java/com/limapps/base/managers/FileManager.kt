package com.limapps.base.managers

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.utils.LogUtil
import com.limapps.base.utils.StringUtils.EMPTY_STRING
import com.limapps.common.className
import com.limapps.common.tryOrDefault
import com.limapps.common.tryOrPrintException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import java.util.Date
import java.util.Locale

object FileManager {

    const val NO_FILE = -1
    const val CHAT = 0
    const val WHIM = 1
    const val PRESCRIPTION = 2
    const val SHARE = 3

    private const val NAME_LOGO = "logo"

    private const val NAME_SHARE_IMAGES_DIRECTORY = "share/"
    private const val NAME_PRESCRIPTION_IMAGES_DIRECTORY = "prescriptions/"
    private const val NAME_CHAT_IMAGES_DIRECTORY = "chat/"
    private const val NAME_WHIMS_IMAGES_DIRECTORY = "whims/"
    private const val NAME_PRODUCTS_DIRECTORY = "products"
    private const val NAME_STORES_DIRECTORY = "store_type"
    private const val NAME_NEW_STORES_DIRECTORY = "new_store_type"
    private const val NAME_MARKETPLACE_DIRECTORY = "marketplace"
    private const val NAME_MARKET_STORE_ICON_DIRECTORY = "stores-detail-icon"
    private const val NAME_MARKET_STORE_FILTER_DIRECTORY = "store_filters_image"
    private const val NAME_LANDING_IMAGE_DIRECTORY = "landing_classification-images"
    private const val NAME_LAUNCH_INFORMATION_DIRECTORY = "launch-information-image"
    private const val NAME_FIDELITY_DIRECTORY = "fidelity"
    private const val NAME_EMOJIS_DIRECTORY = "emojis"
    private const val NAME_RESTAURANT_BACKGROUND = "restaurants_background"
    private const val NAME_RESTAURANT_LOGO = "restaurants_logo"
    private const val NAME_SEARCH_DIRECTORY = "search"
    private const val NAME_PRIVATE_DIRECTORY = "private"
    private const val NAME_RESTAURANTS_TABLECLOTH = "restaurants_first_layer"
    private const val NAME_RESTAURANTS_BACKGROUND = "restaurants_second_layer"
    private const val NAME_STORE_BANNER_IMAGE = "store_banner_image"
    private const val NAME_LOG_ANALYTICS_FILE = "analytics.txt"
    private const val noMediaString = ".nomedia"
    private const val image_profile = "profile_pic.png"

    const val EXTENSION_PNG = ".png"
    const val chat = "chat/"
    const val whims = "whims/"
    const val privates = "private/"
    const val CORRIDORS_SMALL_DIRECTORY = "corridor_small/"

    private var urlRemoteImages: String? = null
    private var externalBasePath: String? = null
    private var externalPrivateBasePath: String? = null

    var directoryBase: String? = null
    var currentPathImages: String? = null

    val fileProfileImage: String get() = pathPrivate + image_profile

    fun setUrlRemoteImages(urlRemotesImages: String) {
        this.urlRemoteImages = urlRemotesImages
    }

    fun inject(context: Context) {
        createBaseDirectory(context)
        createProductsDirectory()
    }

    fun getSoundUri(packageName: String, sound: Int): Uri =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + sound)


    fun saveImage(path: String, source: Bitmap, quality: Int = 100) {
        var fileOutputStream: FileOutputStream? = null
        tryOrPrintException {
            fileOutputStream = FileOutputStream(File(path))
            source.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
        }
        fileOutputStream?.close()
    }

    @Throws(IOException::class)
    fun delete(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                delete(it)
            }
        } else if (file.absolutePath.endsWith("zip")) {
            if (!file.delete()) {
                throw FileNotFoundException("Failed to delete file: $file")
            }
        }
    }

    @Throws(IOException::class)
    fun deleteFilesInDirectory(file: File) {
        if (file.isDirectory) {
            for (directory in file.listFiles()) {
                if (directory.delete()) {
                    LogUtil.e(FileManager.javaClass.name, "File Deleted " + directory.name)
                } else {
                    throw FileNotFoundException("Failed to delete file: $directory")
                }
            }
        }
    }

    fun getStreamFromPath(path: String?): InputStream? {
        return tryOrDefault({ FileInputStream(File(path)) }, null)
    }

    /***
     * LOCAL FILES
     */

    private fun createProductsDirectory() {
        try {
            val oldProductsFolder = File(externalBasePath!! + NAME_PRODUCTS_DIRECTORY)
            if (oldProductsFolder.exists())
                deleteFilesInDirectory(oldProductsFolder)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        createDirectory(pathPrivate + NAME_PRODUCTS_DIRECTORY)
        val noMediaFile = File(pathPrivate + NAME_PRODUCTS_DIRECTORY + File.separator + noMediaString)

        try {
            if (!noMediaFile.exists()) {
                if (!noMediaFile.createNewFile()) {
                    LogUtil.e("noMediaFile", "error")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context, folderType: Int): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"

        val externalFilesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        var path: String = if (externalFilesDirectory?.canWrite() == true) {
            externalFilesDirectory.absolutePath + File.separator
        } else {
            context.filesDir.absolutePath + File.separator
        }

        when (folderType) {
            PRESCRIPTION -> path += NAME_PRESCRIPTION_IMAGES_DIRECTORY
            CHAT -> path += NAME_CHAT_IMAGES_DIRECTORY
            WHIM -> path += NAME_WHIMS_IMAGES_DIRECTORY
            SHARE -> path += NAME_SHARE_IMAGES_DIRECTORY
        }

        createDirectory(path)

        val image = File(path, "$imageFileName.jpg")

        currentPathImages = image.absolutePath

        return image
    }

    @Throws(IOException::class)
    fun createAudioFile(folderType: Int): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val audioFileName = "audioRecorded_" + timeStamp + "_"

        var path = pathPrivate
        when (folderType) {
            CHAT -> path += NAME_CHAT_IMAGES_DIRECTORY
        }
        val storageDir = File(path)
        storageDir.mkdir()

        return File.createTempFile(audioFileName, ".3gp", storageDir)
    }

    private fun createBaseDirectory(context: Context) {
        var nameBaseDirectory = ResourcesProvider.getString(R.string.rappi_app_name)
        nameBaseDirectory = nameBaseDirectory.replace("'", "")
        nameBaseDirectory = nameBaseDirectory.replace(" ", "")
        nameBaseDirectory = nameBaseDirectory.toLowerCase()

        externalBasePath = context.filesDir.absolutePath + File.separator + nameBaseDirectory + File.separator
        externalBasePath?.let {
            createDirectory(it)
            createDirectory(it + NAME_SEARCH_DIRECTORY)
            createDirectory(it + NAME_PRIVATE_DIRECTORY)
        }
        externalPrivateBasePath = context.getExternalFilesDir(null)?.absolutePath
    }

    fun getLocalPathProduct(nameProduct: String?): String = pathPrivate + NAME_PRODUCTS_DIRECTORY + File.separator + nameProduct

    /***
     * REMOTE FILES
     */

    fun getRemoteImage(image: String): String {
        return urlRemoteImages + File.separator + image
    }

    fun getRemotePathStoreImage(storeImage: String): String {
        return urlRemoteImages + File.separator + NAME_STORES_DIRECTORY + File.separator + storeImage
    }

    fun getRemotePathNewStoreImage(storeImage: String): String {
        return urlRemoteImages + File.separator + NAME_NEW_STORES_DIRECTORY + File.separator + storeImage
    }

    fun getRemotePathMarketStoreImage(storeImage: String): String {
        return urlRemoteImages + File.separator + NAME_MARKETPLACE_DIRECTORY + File.separator + storeImage
    }

    fun getRemoteHighPathMarketStoreImage(storeImage: String): String {
        return urlRemoteImages + File.separator + NAME_MARKETPLACE_DIRECTORY + File.separator + storeImage
    }

    fun getRemoteCPGSImageBanner(storeImage: String): String {
        return urlRemoteImages + File.separator + "cpgs_banners" + File.separator + storeImage
    }

    fun getRemoteStoreIconImage(image: String): String {
        return urlRemoteImages + File.separator + NAME_MARKET_STORE_ICON_DIRECTORY + File.separator + image
    }

    fun getRemoteMarketLandingImage(imagePath: String): String {
        return urlRemoteImages + File.separator + NAME_LANDING_IMAGE_DIRECTORY + File.separator + imagePath
    }

    fun getRemoteMarketLaunchInformationImage(imagePath: String): String {
        return "$urlRemoteImages${File.separator}$NAME_LAUNCH_INFORMATION_DIRECTORY${File.separator}$imagePath"
    }

    fun getRemoteFidelityImage(imagePath: String): String {
        return "$urlRemoteImages${File.separator}$NAME_FIDELITY_DIRECTORY${File.separator}$imagePath"
    }

    fun getRemotePathCorridors(storeImage: String, prefix: String): String {
        return "$urlRemoteImages${File.separator}$prefix$storeImage"
    }

    fun getRemotePathStoreFilter(image: String): String {
        return "$urlRemoteImages${File.separator}$NAME_MARKET_STORE_FILTER_DIRECTORY${File.separator}$image"
    }

    fun getRemotePathForEmojiImage(emojiImageUrl: String?): String {
        return "$urlRemoteImages${File.separator}$NAME_EMOJIS_DIRECTORY${File.separator}$emojiImageUrl"
    }

    fun getRemotePathForCorridorSmall(corridorIconUrl: String?): String {
        return "$urlRemoteImages${File.separator}$CORRIDORS_SMALL_DIRECTORY$corridorIconUrl"
    }

    fun getRemotePathProduct(nameProduct: String?): String {
        return urlRemoteImages +
                File.separator + NAME_PRODUCTS_DIRECTORY +
                File.separator + nameProduct
    }

    fun getRemotePathProduct(nameProduct: String?, quality: String?): String {
        return quality?.let {
            urlRemoteImages +
                    File.separator + NAME_PRODUCTS_DIRECTORY +
                    File.separator + quality +
                    File.separator + nameProduct
        } ?: kotlin.run { getRemotePathProduct(nameProduct) }
    }

    fun getRemotePathRestaurantImage(background: String): String {
        return urlRemoteImages + File.separator + NAME_RESTAURANT_BACKGROUND +
                File.separator + background
    }

    fun getRemotePathRestaurantLogo(logo: String): String {
        return urlRemoteImages + File.separator + NAME_RESTAURANT_LOGO + File.separator + logo
    }

    fun getRemotePathRestaurantBackground(background: String?): String {
        val bg = background ?: "123456789.png"
        return urlRemoteImages +
                File.separator + NAME_RESTAURANTS_BACKGROUND +
                File.separator + bg
    }

    fun getRemotePathTablecloth(tablecloth: String): String {
        return urlRemoteImages +
                File.separator + NAME_RESTAURANTS_TABLECLOTH +
                File.separator + tablecloth
    }

    fun getRemotePathStoreBannerImage(bannerImage: String?): String =
            urlRemoteImages + File.separator + NAME_STORE_BANNER_IMAGE + File.separator + bannerImage

    fun getRemotePathBrandRoomBackgroundImage(background: String): String {
        return urlRemoteImages + File.separator + background
    }

    fun getAdsRemotePathImage(imageUrl: String): String {
        return if (isAdsUrlPattern(imageUrl)) {
            imageUrl
        } else {
            urlRemoteImages + File.separator + imageUrl
        }
    }

    val pathPrivate: String
        get() = externalBasePath + NAME_PRIVATE_DIRECTORY + File.separator

    fun getRemotePathPromotionBannerImage(bannerImage: String): String =
            urlRemoteImages + File.separator + bannerImage

    private fun createDirectory(path: String): Boolean {
        val fileExternalBasePath = File(path)
        if (fileExternalBasePath.exists().not()) {
            if (fileExternalBasePath.mkdir().not()) {
                LogUtil.e(FileManager.className(), "Problem creating directory $path")
                return false
            }
        }
        return true
    }

    fun logAnalyticsEventToFile(event: String, params: JSONObject?) {
        if (isExternalStorageWritable()) {
            val logEntry = JSONObject(mapOf(event to (params ?: EMPTY_STRING)))
            val logFile = getPrivateStorageFileFolder(NAME_LOG_ANALYTICS_FILE)
            appendStringToFile(logFile, logEntry.toString().plus(File.separator))
        }
    }

    private fun getPrivateStorageFileFolder(folderName: String): File {
        val file = File(externalPrivateBasePath.plus(File.separator).plus(folderName))
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
        return file
    }

    private fun appendStringToFile(filename: File, content: String) {
        FileOutputStream(filename, true).use { fos -> fos.write(content.toByteArray()) }
    }

    private fun isExternalStorageWritable() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun generateUriForPicture(context: Context, folderType: Int, applicationId: String): Uri {
        val file = createImageFile(context, folderType)
        return FileProvider.getUriForFile(context, "$applicationId.provider", file)
    }

    fun generateUriForFile(context: Context, existingFile: File, applicationId: String): Uri {
        return FileProvider.getUriForFile(context, "$applicationId.provider", existingFile)
    }

    fun rotateImageIfRequired(file: File): Bitmap {
        val options = BitmapFactory.Options().apply { inSampleSize = 6 }
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
        val ei = ExifInterface(file.absolutePath)
        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 90)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, rotation: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotation.toFloat())
        val rotatedImg = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedImg
    }

    private fun isAdsUrlPattern(url: String?): Boolean {
        val pattern = Pattern.compile("^https?://.*\$")
        return pattern.matcher(url).find()
    }
}