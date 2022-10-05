'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';
const RESOURCES = {
  ".svn/all-wcprops": "9c5a001f5820bce776d2e4aa82ee9509",
".svn/entries": "c2ed554d4d5eb813210cb9b2463e562a",
".svn/prop-base/favicon.png.svn-base": "113136892f2137aa0116093a524ade0b",
".svn/text-base/favicon.png.svn-base": "5dcef449791fa27946b3d35ad8803796",
".svn/text-base/index.html.svn-base": "22cb290b8ff6831e0ff8f7d9a9ff89b4",
".svn/text-base/manifest.json.svn-base": "8cba1317667fbaa095ae6ec3a8da21e3",
"assets/AssetManifest.json": "4f963d3dd559eb7e3bf3d7769a8c0fa6",
"assets/assets/images/authoriesusers.png": "61ebf1a9d0e64b4a0fb14d6689e45b1e",
"assets/assets/images/completedusers.png": "ad69340628f2b31a705504a38cda36de",
"assets/assets/images/download.png": "a97df71273971ffa8e5ff3054f9a1460",
"assets/assets/images/finishusers.png": "0c124c3ea9a5ccb966685ced086fd4b5",
"assets/assets/images/firstuser.png": "9cf087f8794d99f2180442d21e36c229",
"assets/assets/images/inprogress.png": "e50755eac5567a3c0fbb90826f96b9b1",
"assets/assets/images/otpverified.png": "dc2e33577694129e62da422e910328a8",
"assets/assets/images/panverified.png": "813e3181b96b08903d4810d359817f0c",
"assets/assets/images/vitt_logo.png": "686ebb57078600079e60b783efe3021f",
"assets/FontManifest.json": "7d32767de9fa0dd437f32f47f681b5d5",
"assets/fonts/MaterialIcons-Regular.otf": "95db9098c58fd6db106f1116bae85a0b",
"assets/lib/Resources/Fonts/Roboto/Roboto-Black.ttf": "d6a6f8878adb0d8e69f9fa2e0b622924",
"assets/lib/Resources/Fonts/Roboto/Roboto-Regular.ttf": "8a36205bd9b83e03af0591a004bc97f4",
"assets/lib/Resources/Images/email.png": "797cf4391f4b5aab0230ed3e3c5c8fcb",
"assets/lib/Resources/Images/empty_cart.png": "a83a41b976945d47d574891ddd9d2e35",
"assets/lib/Resources/Images/home_authorized_user_icon.png": "fa26125b357c332a70929cadeee00fd9",
"assets/lib/Resources/Images/home_completed_user_icon.png": "f3291b9271b649446dcfd009c7bc12d6",
"assets/lib/Resources/Images/home_finish_user_icon.png": "90414dc55fe3241af682526df49387c5",
"assets/lib/Resources/Images/home_first_time_user_icon.png": "848c70784b7f7de27ffb30004c6aaa30",
"assets/lib/Resources/Images/home_in_process_icon.png": "88e1bd20929edad322245bac895d9c54",
"assets/lib/Resources/Images/home_otp_verified_icon.png": "fb87282eb929183f298ad71c84172ea8",
"assets/lib/Resources/Images/home_pan_verified_icon.png": "7843a52194f7dcd326b98712fa049cf8",
"assets/lib/Resources/Images/login_bg_image.png": "282d489708ae9e3af71eaab7c76ff011",
"assets/lib/Resources/Images/log_out.png": "ccdaf1f2c565d05747357aa804a84813",
"assets/lib/Resources/Images/phonecall.png": "f0806cd1fa4e827caf7207295dd019af",
"assets/lib/Resources/Images/placeholder_doc_image.png": "a0a2fcae1b80370bf3a624ad7e53ae4b",
"assets/lib/Resources/Images/vitt_logo.png": "686ebb57078600079e60b783efe3021f",
"assets/NOTICES": "04f138fdc43e5eab1d69cf6de0fc6d4b",
"assets/packages/awesome_dialog/assets/flare/error.flr": "e3b124665e57682dab45f4ee8a16b3c9",
"assets/packages/awesome_dialog/assets/flare/info.flr": "bc654ba9a96055d7309f0922746fe7a7",
"assets/packages/awesome_dialog/assets/flare/info2.flr": "21af33cb65751b76639d98e106835cfb",
"assets/packages/awesome_dialog/assets/flare/info_without_loop.flr": "cf106e19d7dee9846bbc1ac29296a43f",
"assets/packages/awesome_dialog/assets/flare/question.flr": "1c31ec57688a19de5899338f898290f0",
"assets/packages/awesome_dialog/assets/flare/succes.flr": "ebae20460b624d738bb48269fb492edf",
"assets/packages/awesome_dialog/assets/flare/succes_without_loop.flr": "3d8b3b3552370677bf3fb55d0d56a152",
"assets/packages/awesome_dialog/assets/flare/warning.flr": "68898234dacef62093ae95ff4772509b",
"assets/packages/awesome_dialog/assets/flare/warning_without_loop.flr": "c84f528c7e7afe91a929898988012291",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "6d342eb68f170c97609e9da345464e5e",
"canvaskit/canvaskit.js": "c2b4e5f3d7a3d82aed024e7249a78487",
"canvaskit/canvaskit.wasm": "4b83d89d9fecbea8ca46f2f760c5a9ba",
"canvaskit/profiling/canvaskit.js": "ae2949af4efc61d28a4a80fffa1db900",
"canvaskit/profiling/canvaskit.wasm": "95e736ab31147d1b2c7b25f11d4c32cd",
"favicon.png": "5dcef449791fa27946b3d35ad8803796",
"flutter.js": "eb2682e33f25cd8f1fc59011497c35f8",
"icons/.svn/all-wcprops": "becdd1a36284c0e8196091de22bbfc01",
"icons/.svn/entries": "a0c41353677bbba1554a873dd4a524bb",
"icons/.svn/prop-base/Icon-192.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/prop-base/Icon-512.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/prop-base/Icon-maskable-192.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/prop-base/Icon-maskable-512.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/text-base/Icon-192.png.svn-base": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/.svn/text-base/Icon-512.png.svn-base": "96e752610906ba2a93c65f8abe1645f1",
"icons/.svn/text-base/Icon-maskable-192.png.svn-base": "c457ef57daa1d16f64b27b786ec2ea3c",
"icons/.svn/text-base/Icon-maskable-512.png.svn-base": "301a7604d45b3e739efc881eb04896ea",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-maskable-192.png": "c457ef57daa1d16f64b27b786ec2ea3c",
"icons/Icon-maskable-512.png": "301a7604d45b3e739efc881eb04896ea",
"index.html": "d71b55fd248a28c6b7d88663bf8aa6f9",
"/": "d71b55fd248a28c6b7d88663bf8aa6f9",
"main.dart.js": "9b2272aae09b14b0b0a0fe6f2b48c3af",
"manifest.json": "8cba1317667fbaa095ae6ec3a8da21e3",
"version.json": "f4af090479c6658f21c651ed47e7227b"
};

// The application shell files that are downloaded before a service worker can
// start.
const CORE = [
  "main.dart.js",
"index.html",
"assets/NOTICES",
"assets/AssetManifest.json",
"assets/FontManifest.json"];
// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});

// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});

// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache.
        return response || fetch(event.request).then((response) => {
          cache.put(event.request, response.clone());
          return response;
        });
      })
    })
  );
});

self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});

// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}

// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
