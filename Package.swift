// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "AudioClient",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "AudioClient",
            targets: ["AudioClientPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "AudioClientPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AudioClientPlugin"),
        .testTarget(
            name: "AudioClientPluginTests",
            dependencies: ["AudioClientPlugin"],
            path: "ios/Tests/AudioClientPluginTests")
    ]
)