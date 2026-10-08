package mx.tec.avisos.data.imagenes

import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import mx.tec.avisos.fotoDePrueba
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.max

/**
 * La prueba que faltaba: ningún mutante del compresor la ponía en rojo, porque
 * nadie comprobaba el tamaño de lo que sale. Se mide que la imagen resultante
 * no exceda el tamaño máximo de 1280 px por lado.
 */
@RunWith(AndroidJUnit4::class)
class CompresorDeImagenTest {

    private val contexto = InstrumentationRegistry.getInstrumentation().targetContext
    private val compresor = CompresorDeImagen(contexto)

    @Test
    fun una_foto_grande_se_reduce_al_tamano_maximo() = runBlocking {
        val uriOriginal = fotoDePrueba(contexto, 2400, 1800)

        val bytesCompresion = compresor.comprimir(uriOriginal)
        val bitmapResultado = BitmapFactory.decodeByteArray(bytesCompresion, 0, bytesCompresion.size)

        assertTrue("La imagen comprimida no debió ser nula", bitmapResultado != null)
        val ladoMayor = max(bitmapResultado.width, bitmapResultado.height)
        assertTrue("El lado mayor ($ladoMayor px) debió ser <= 1280 px", ladoMayor <= 1280)
    }
}
