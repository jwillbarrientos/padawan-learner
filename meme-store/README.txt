simple-http-server < spring framework/libreria

meme-store < app final - endpoints, http endpoint, REST API, REST Interface,

	JonaServer jonaServer = new JonaServer(8080);

	public static class EtiquetaController {
	    // para responder a requests del tipo:
	    // http://localhost:8080/etiqueta?nombre=publico-general&otroValor=valor3
		Response newEtiqueta(Request req) {
			String nombreEtiqueta = req.getQueryParameter("nombre");
			//publico-general == public-general
			String body = req.getBody();
			// jdbc insertar nombre en la BD
			return new Response(200,"hjgfd")
		}
	}

	EtiquetaController etiquetaController = new EtiquetaController();

	- publicar endpoints
		jonaServer.registerEndpoint(Method.POST, "/etiqueta", etiquetaController::newEtiqueta)
		                                                        Function<Request, Response>
	- servir contenido estatico
		jonaServer.addStaticContent("/static"); /
			/index.html -> /static/index.html
			/favicon.ico -> /static/favicon.ico
			/images/bg.jpg -> /static/images/bg.jpg
			/js/main.js -> /static/js/main.js

    jonaServer.start();

