'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';
const RESOURCES = {
  ".svn/all-wcprops": "2ba8e4fcb93ba6172d1aa30b6f917ea6",
".svn/entries": "331f87592a21a80ce1e2d031a125e11b",
".svn/prop-base/favicon.png.svn-base": "113136892f2137aa0116093a524ade0b",
".svn/text-base/favicon.png.svn-base": "5dcef449791fa27946b3d35ad8803796",
".svn/text-base/index.html.svn-base": "517c35d27b082e64ddc6ef2901f4cd78",
".svn/text-base/manifest.json.svn-base": "4965a4b9dd33a8de3fe153df0b8609fe",
"assets/asset/fonts/GothicBlack.ttf": "73c160630984c57e4b77b819016689ed",
"assets/asset/fonts/GothicBold.ttf": "f53ebb7bc7eb4957d6aa13867cb66f42",
"assets/asset/fonts/GothicExtraBold.ttf": "c79e82c21006bea2986801a44229987e",
"assets/asset/fonts/GothicExtraLight.ttf": "817c34abcfdc4aa86be7d654808a0493",
"assets/asset/fonts/GothicLight.ttf": "2efc91cd70fc5b47b1249c9e7ddd64d2",
"assets/asset/fonts/GothicMedium.ttf": "c9d7fd21e8d463a4e5f0390fd6016645",
"assets/asset/fonts/GothicRegular.ttf": "e742f688ceac24b94fedc826b19a43df",
"assets/asset/fonts/GothicSemiBold.ttf": "57d3eeab0ade8b9e08f509e41ed4f2cb",
"assets/asset/fonts/GothicThin.ttf": "39b303cfa7ebfe70f9d7544357909480",
"assets/asset/images/backgroudicon.png": "1520670e009480d8b06893c804ebaa76",
"assets/asset/images/bank.png": "4506ac13f03eebd318d0dcf6485127cb",
"assets/asset/images/bankheader.png": "2b808a54505deead7dc1f1af81042eb2",
"assets/asset/images/bank_selected.png": "0c770875789869d468410b7629e30fc3",
"assets/asset/images/calendar.png": "bc61555b0b0f2309ba360766a196fb04",
"assets/asset/images/Checklist.png": "40afc0ec741f5a700deb98d1065a090d",
"assets/asset/images/document.png": "bbde03798351c3589a8fbbd6e6505518",
"assets/asset/images/document_selected.png": "a08d70714281b7eaac12b914e638905b",
"assets/asset/images/email_id.png": "0b07ae7759ffcea234484baee9dd789b",
"assets/asset/images/esign.png": "37225589d1cda9d518e7133c097731c2",
"assets/asset/images/esignselected.png": "1b06e6a09652a5af71d0e4241fca0633",
"assets/asset/images/Esign_selected.png": "4f6953f140df21bfc9e0b2bb8a46776d",
"assets/asset/images/flutter-logo.png": "866e2e72133d50974c959b01de1acd8b",
"assets/asset/images/full_name.png": "b402d230b476f17befb6cfaa8a6394d4",
"assets/asset/images/icon.png": "b3af7461058424c347aa70380cd10d4c",
"assets/asset/images/ipv.png": "c5c7759d5f31ac1dd2092e6beb53ad61",
"assets/asset/images/ipvheader.png": "3e065034509f0e6a7737b64f18aacb6c",
"assets/asset/images/ipv_selected.png": "40fcba7d441e154bc17cf4ba61bda3cb",
"assets/asset/images/mobile_number.png": "1363bcc9181d6477bf6063973435793a",
"assets/asset/images/pan.png": "c5c7759d5f31ac1dd2092e6beb53ad61",
"assets/asset/images/pan_selected.png": "79df027e02ca50437432604edc43f388",
"assets/asset/images/payment.png": "773a0d7e721ec853f787e4264c68290c",
"assets/asset/images/paymentheader.png": "be87774269f7add3e6043b34c15f8f91",
"assets/asset/images/payment_selected.png": "0147e5d932a349a15efba75b63d83110",
"assets/asset/images/personaldetails.png": "831557f4fe47dc2dda40d1a13d2aa15b",
"assets/asset/images/personal_header.png": "aab6ca9257bdd4f02974bb510338abf8",
"assets/asset/images/personal_selected.png": "8275801cba790711c8fd0e125a664c6a",
"assets/asset/images/processcomplete.png": "0d501b905d4948ee68cbc5fd39e246e6",
"assets/asset/images/right_tick.png": "209f00b3a5ab0a73faaa1609e3eab9da",
"assets/asset/images/signout.png": "ffa1c360cf962583ab1cb17e3748d0e6",
"assets/asset/images/signoutwhite.png": "2d76ab74f734fda92fe2b3d52592c9a0",
"assets/asset/images/success_otp.png": "3b6d9216f322a5fc20c7e96fc7659671",
"assets/asset/images/uploadblack.png": "408e94a7247cb0914dcd762cc5e047c8",
"assets/asset/images/uploadheader.png": "93d5ff07f732a12c30f65915af85939b",
"assets/asset/images/vishwasicon-modified.png": "6c6dd4a793981b1a6e82b1f6056ffcab",
"assets/asset/images/vitt_logo.png": "686ebb57078600079e60b783efe3021f",
"assets/asset/pdf/certificate.pfx": "eb61b66a9d32bc4d083612cc30b12d61",
"assets/asset/pdf/digital_signature_template.pdf": "a87951b49296547517e23a6ab3d40455",
"assets/asset/pdf/MasterForm.pdf": "cd748f4d66b6c881c10df420ab713e9f",
"assets/asset/pdf/MasterFormSilverStream.pdf": "a0c8c282ed9aa95dbc0efe0fbe250339",
"assets/asset/pdf/MasterForm_25_34.pdf": "c2476b3b719c01377da4ae40fe616a54",
"assets/asset/pdf/MasterForm_5_27.pdf": "11601f8c41f494815999ca7c4d21d48b",
"assets/asset/pdf/MasterForm_5_34.pdf": "84dacf9d3357d0ec0f9f984bb4d184f1",
"assets/asset/svg/bank_svg.svg": "d1ab468c75804eee50c1e5d74429021d",
"assets/asset/svg/calendar.svg": "0693d97bdd8fc4732674e5ff5845e4e3",
"assets/asset/svg/document_svg.svg": "75284500c29a9266e30a6f455bee1e4a",
"assets/asset/svg/email_id.svg": "86bfa145d19ec947f902adfed7509a00",
"assets/asset/svg/esign_svg.svg": "794fa1f7dfe37435ef38943c69265cc3",
"assets/asset/svg/full_name.svg": "e03b3a1c55fbff76d77fe6342e99fb13",
"assets/asset/svg/ipv_svg.svg": "28f63886a5a37ce80d192cfd6342e08f",
"assets/asset/svg/mobile.svg": "a90025cef265c5abb8bb109d0acd536f",
"assets/asset/svg/pancard_svg.svg": "40ee97864551c90fa0596adbbd76d54b",
"assets/asset/svg/payment_svg.svg": "c14db06d65aa4f9e17b8d154185e9822",
"assets/asset/svg/personal_details_svg.svg": "c03adc536a4c59482724f5a0e73719a6",
"assets/asset/svg/processcomplete_svg.svg": "49b8269592075578cc9e965dcdbcfdb3",
"assets/asset/svg/signout.svg": "94fe4c526876722d646fb9962f989d0e",
"assets/AssetManifest.json": "2b55958169205556b1e6da4189f7493c",
"assets/FontManifest.json": "4c13cba93d36c18a30f37f412bd75981",
"assets/fonts/MaterialIcons-Regular.otf": "95db9098c58fd6db106f1116bae85a0b",
"assets/NOTICES": "74942c54053623f23da7b3f3bd41a3be",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "6d342eb68f170c97609e9da345464e5e",
"canvaskit/canvaskit.js": "c2b4e5f3d7a3d82aed024e7249a78487",
"canvaskit/canvaskit.wasm": "4b83d89d9fecbea8ca46f2f760c5a9ba",
"canvaskit/profiling/canvaskit.js": "ae2949af4efc61d28a4a80fffa1db900",
"canvaskit/profiling/canvaskit.wasm": "95e736ab31147d1b2c7b25f11d4c32cd",
"favicon.png": "5dcef449791fa27946b3d35ad8803796",
"flutter.js": "eb2682e33f25cd8f1fc59011497c35f8",
"icons/.svn/all-wcprops": "9dffe5ee0d4ded69d05fd430a3b40d2d",
"icons/.svn/entries": "534e7fcd8735df1c9717320202e39726",
"icons/.svn/prop-base/Icon-192.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/prop-base/Icon-512.png.svn-base": "113136892f2137aa0116093a524ade0b",
"icons/.svn/text-base/Icon-192.png.svn-base": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/.svn/text-base/Icon-512.png.svn-base": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"index.html": "828e130065f4fc7457049e351d9765a4",
"/": "828e130065f4fc7457049e351d9765a4",
"main.dart.js": "e00f5c159c48503d57ac11384db9061f",
"manifest.json": "4965a4b9dd33a8de3fe153df0b8609fe",
"version.json": "88f71fc79899025cc48a9f064de4a4f1"
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
