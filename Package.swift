// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorCommunityYoutubeDl",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorCommunityYoutubeDl",
            targets: ["YoutubeDownloaderPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "YoutubeDownloaderPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/YoutubeDownloaderPlugin"),
        .testTarget(
            name: "YoutubeDownloaderPluginTests",
            dependencies: ["YoutubeDownloaderPlugin"],
            path: "ios/Tests/YoutubeDownloaderPluginTests")
    ]
)